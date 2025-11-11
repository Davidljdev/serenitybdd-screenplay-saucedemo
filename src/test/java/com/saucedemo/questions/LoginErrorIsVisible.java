package com.saucedemo.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/*
  Question para comprobar si el mensaje de error de login es visible.

  Comentarios sobre diseño Screenplay y este fichero:
  - Question<T>:
    * Representa una consulta/lectura del estado de la aplicación (no realiza acciones).
    * Devuelve un valor (aquí Boolean) que luego se usa en aserciones:
        actor.should(seeThat(LoginErrorIsVisible.isVisible(), is(true)));
    * Las Questions mantienen la lógica de verificación separada de los Steps y Tasks,
      lo que mejora la reutilización y las pruebas unitarias.

  - Target:
    * Encapsula el localizador del elemento UI (una sola fuente de verdad).
    * Usar Target evita repetir By.* por todo el código y facilita mantenimiento.

  - resolvedFor(actor):
    * Traduce el Target a WebElement en el contexto del actor (usa la Ability BrowseTheWeb).
    * Permite que el actor "pregunte" al sistema sobre el estado de un elemento.

  - ¿Por qué este diseño?
    * Separación de responsabilidades:
      - Tasks = hacen cosas (interacciones).
      - Questions = leen el estado (verificaciones).
      - StepDefinitions = orquestan Tasks/Questions sin manipular WebDriver.
    * Facilita el reporting: cada interacción y pregunta queda registrada en los reports.

  - Manejo de errores:
    * Se usa try/catch para devolver false si el elemento no existe o no es accesible,
      evitando que una excepción técnica rompa la ejecución de la Question.
*/

public class LoginErrorIsVisible implements Question<Boolean> {

    // Centraliza el selector del mensaje de error. Ajustar selector si la app cambia.
    private static final Target ERROR_MESSAGE =
            Target.the("login error message").located(By.cssSelector("[data-test='error'], .error-message-container"));

    /**
     * answeredBy:
     * - Método obligatorio de la interfaz Question<Boolean>.
     * - Recibe un Actor y debe devolver el valor de la consulta (true/false).
     * - No realiza acciones; solo inspecciona el estado visible en la UI.
     */
    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            // resolveFor(actor) obtiene el WebElementFacade en el contexto del actor.
            // isVisible() devuelve true si el elemento está presente y visible.
            return ERROR_MESSAGE.resolveFor(actor).isVisible();
        } catch (Exception e) {
            // Si no se encuentra el elemento o hay cualquier excepción, devolvemos false.
            // Esto hace la Question robusta frente a cambios temporales en la UI.
            return false;
        }
    }

    /**
     * Factory method:
     * - Proporciona una forma legible y fluida de usar la Question en los Steps:
     *     actor.should(seeThat(LoginErrorIsVisible.isVisible(), is(true)));
     * - Evita instanciar directamente con "new" en los StepDefinitions.
     */
    public static LoginErrorIsVisible isVisible() {
        return new LoginErrorIsVisible();
    }
}
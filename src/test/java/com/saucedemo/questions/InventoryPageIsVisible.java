package com.saucedemo.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/*
  Question: InventoryPageIsVisible

  Comentarios por secciones (explicación breve y localizada):
  - Qué es una Question:
    * Es una abstracción de Screenplay para "leer" el estado de la aplicación.
    * No debe realizar acciones (como clicks o navegación), solo consultar y devolver un valor.
    * Se usa típicamente en aserciones: actor.should(seeThat(InventoryPageIsVisible.isVisible(), is(true)));

  - Diseño general:
    * Mantén la lógica de verificación fuera de los Steps y Tasks para favorecer la
      reutilización y pruebas unitarias de la lógica de verificación.
*/

/* -------------------------------------------------------------
   Target estático: por qué static final
   -------------------------------------------------------------
   - Centraliza el selector del contenedor de inventario.
   - static: evita recrearlo por cada instancia; es un recurso inmutable compartido.
   - final (implícito en la práctica): no se reasigna; el selector es estable.
   - Encapsular el By aquí facilita mantenimiento si el DOM cambia.
*/
public class InventoryPageIsVisible implements Question<Boolean> {

    // Localizador ejemplo: ajusta si tu aplicación usa otro id/selector
    private static final Target INVENTORY_CONTAINER =
            Target.the("inventory container").located(By.id("inventory_container"));

    /* -------------------------------------------------------------
       answeredBy(Actor actor): qué hace y por qué
       -------------------------------------------------------------
       - Método obligatorio de la interfaz Question<Boolean>.
       - Recibe el Actor que "pregunta" y devuelve el valor de la consulta.
       - No realiza acciones mutables; sólo inspecciona (resolveFor + isVisible).
       - Uso típico:
           Boolean visible = new InventoryPageIsVisible().answeredBy(actor);
           actor.should(seeThat(InventoryPageIsVisible.isVisible(), is(true)));
       - Se usa try/catch para devolver false en caso de errores (elemento no presente),
         evitando que una excepción técnica rompa la verificación y permitiendo mensajes claros.
    */
    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            // resolveFor(actor) obtiene el WebElementFacade usando la Ability BrowseTheWeb del actor.
            // isVisible() devuelve verdadero si el elemento está presente y visible en la UI.
            return INVENTORY_CONTAINER.resolveFor(actor).isVisible();
        } catch (Exception e) {
            // Devolver false cuando no se encuentra/accede al elemento hace la Question robusta.
            return false;
        }
    }

    /* -------------------------------------------------------------
       viewedBy(Actor actor): compatibilidad/estilo legacy
       -------------------------------------------------------------
       - Método estático de compatibilidad con código antiguo que llamaba InventoryPageIsVisible.viewedBy(actor).
       - Implementación mediante actor.asksFor(...) que es la forma idiomática de pedir una Question.
       - Por qué usar actor.asksFor:
         * Es la API Screenplay para que un Actor "pregunte" algo y reciba la respuesta.
         * Mantiene trazabilidad en reporting (qué Actor preguntó qué).
    */
    // Compatibilidad con llamadas existentes: InventoryPageIsVisible.viewedBy(actor)
    public static Boolean viewedBy(Actor actor) {
        return actor.asksFor(new InventoryPageIsVisible());
    }

    /* -------------------------------------------------------------
       Factory idiomática isVisible(): por qué y cuándo usarla
       -------------------------------------------------------------
       - Proporciona una lectura fluida en las assertions:
           actor.should(seeThat(InventoryPageIsVisible.isVisible(), is(true)));
       - Evita usar 'new' en los step definitions.
       - Es la forma recomendada para Questions pequeñas sin parámetros.
    */
    public static InventoryPageIsVisible isVisible() {
        return new InventoryPageIsVisible();
    }

    /* -------------------------------------------------------------
       Alias/semántica alternativa toTheUser()
       -------------------------------------------------------------
       - A veces se añaden factory methods alternativos para mejorar la lectura
         en contextos distintos (p. ej. InventoryPageIsVisible.toTheUser()).
       - No hay efecto técnico diferente; solo mejora la expresividad del test.
    */
    public static InventoryPageIsVisible toTheUser() {
        return new InventoryPageIsVisible();
    }
}

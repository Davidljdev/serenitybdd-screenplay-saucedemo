package com.saucedemo.tasks;

import com.saucedemo.model.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/*
  Task Login: encapsula la secuencia de acciones para hacer login.
  Uso sugerido en Screenplay:
    actor.attemptsTo(Login.with(user));
*/

/* -------------------------------------------------------------
   Por qué esta clase implementa Task
   -------------------------------------------------------------
   - Task es la abstracción Screenplay para "hacer" algo (una unidad de comportamiento).
   - Implementar Task permite que un Actor ejecute esta tarea con actor.attemptsTo(...),
     que a su vez permite reporting y trazabilidad de cada interacción en los informes.
*/

public class Login implements Task {

    /* -------------------------------------------------------------
       Por qué 'user' es private final
       -------------------------------------------------------------
       - private: encapsulación; el estado no debe ser accesible directamente desde fuera.
       - final: la Task es inmutable respecto al usuario que contiene; eso evita efectos colaterales
         y facilita razonar sobre el comportamiento (Thread-safe en el sentido de no mutar estado interno).
       - Ejemplo: cada vez que se crea Login.with(user) se obtiene una Task con datos invariables.
    */
    private final User user;

    /* -------------------------------------------------------------
       Por qué los Target son private static final
       -------------------------------------------------------------
       - Target centraliza selectores de UI (una sola fuente de verdad).
       - static: el selector pertenece a la clase, no a la instancia; no hace falta recrearlo por cada Task.
       - final: el Target no debe cambiar en tiempo de ejecución.
       - private: mantener encapsulado el detalle técnico de localización.
       - Beneficio: si cambia el id/css sólo se modifica aquí.
    */
    private static final Target USERNAME = Target.the("username field").located(By.id("user-name"));
    private static final Target PASSWORD = Target.the("password field").located(By.id("password"));
    private static final Target LOGIN_BUTTON = Target.the("login button").located(By.id("login-button"));

    /* -------------------------------------------------------------
       Constructor
       -------------------------------------------------------------
       - Recibe el modelo User que contiene username/password.
       - Se asigna al campo final para mantener inmutabilidad.
    */
    public Login(User user) {
        this.user = user;
    }

    /* -------------------------------------------------------------
       Factory method estático (por qué usarlo)
       -------------------------------------------------------------
       - Mejora la legibilidad en los Steps: Login.with(user) es más declarativo que new Login(user).
       - Permite cambiar la forma de creación sin afectar los consumidores.
       - Es un patrón recomendado en Screenplay (fluidez en los tests).
       - Ejemplo de uso:
           actor.attemptsTo(Login.with(new User("u","p")));
    */
    public static Login with(User user) {
        return new Login(user);
    }

    /* -------------------------------------------------------------
       performAs: por qué se llama así y por qué se sobrescribe
       -------------------------------------------------------------
       - Task define el contrato performAs(Actor actor) que la implementación debe cumplir.
       - performAs es invocado por el framework cuando el Actor ejecuta la Task.
       - Al sobrescribir performAs proporcionamos la lógica concreta de la Task.
       - El nombre refleja "perform as the given actor" — ejecutar la tarea en el contexto del actor.
    */
    @Override
    public <T extends Actor> void performAs(T actor) {
        /*
          - Nota sobre la firma genérica <T extends Actor>:
            * Permite que el método acepte subtipos específicos de Actor si existiesen (extensible).
            * En la práctica se usa Actor directamente; la generics signature viene del contrato de Task.
          - Por qué no usamos simplemente Actor actor:
            * Se sigue la firma de la interfaz Task<T> para máxima compatibilidad con el framework.
        */

        /*
          actor.attemptsTo(...)
          - attemptsTo es la forma Screenplay de decir "intenta ejecutar estas Interactions/Tasks".
          - Recibe una lista de Interactions/Tasks; cada una se registra para reporting.
          - Regla práctica: usar actor.attemptsTo para ejecutar Tasks/Interactions desde Steps o desde otras Tasks.
            No se suele usar attemptsTo para acciones internas no relacionadas con Screenplay.
          - attemptsTo permite composición: dentro de una Task puedes llamar a otras Tasks o Interactions.
        */
        actor.attemptsTo(
            /*
              Enter.theValue(...).into(TARGET)
              - Interaction que introduce texto en un campo.
              - Cada Interaction genera un step separado en los reports cuando
                serenity.take.screenshots=FOR_EACH_ACTION / FOR_EACH_STEP.
            */
            Enter.theValue(user.getUsername()).into(USERNAME),

            /*
              Enter para la contraseña.
              - Es otra Interaction separada: produce su propia línea en el reporte y su captura.
            */
            Enter.theValue(user.getPassword()).into(PASSWORD),

            /*
              Click.on(LOGIN_BUTTON)
              - Interaction que hace click en el elemento.
              - También se registra en el reporte.
            */
            Click.on(LOGIN_BUTTON)
        );
    }
}

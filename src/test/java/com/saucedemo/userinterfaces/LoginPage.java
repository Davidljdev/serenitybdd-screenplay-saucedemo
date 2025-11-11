package com.saucedemo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/*
  LoginPage: Objetivo
  - Clase que centraliza los selectores (Targets) de la pantalla de login.
  - En Screenplay esta clase actúa como "UI map": Tasks/Interactions/Questions referencian
    estos Targets en lugar de usar By.* dispersos por el código.
  - Beneficio: una única fuente de verdad para localizadores facilita mantenimiento.
*/

public class LoginPage {

    /* USERNAME_FIELD
       - public: permite uso directo desde Tasks/Interactions en tests (ej: Enter.theValue(...).into(LoginPage.USERNAME_FIELD)).
       - static final: el Target es inmutable y compartido por todas las instancias; no hay necesidad de crearlo cada vez.
       - Se usa XPath aquí porque es explícito; si es posible prefiera By.id o selectores CSS por simplicidad y rendimiento.
       - Nombre claro y descriptivo para mejorar la lectura de Tasks.
    */
    public static final Target USERNAME_FIELD = Target.the("campo de usuario")
            .located(By.xpath("//input[@id='user-name']"));

    /* PASSWORD_FIELD
       - Misma razón que USERNAME_FIELD: centralizar el localizador y hacerlo reutilizable.
       - Mantener consistencia en la forma de localizar (id, css, xpath) facilita refactorizaciones.
    */
    public static final Target PASSWORD_FIELD = Target.the("campo de contraseña")
            .located(By.xpath("//input[@id='password']"));

    /* LOGIN_BUTTON
       - Target para el botón de login.
       - Las Tasks usarán este Target para Click.on(LoginPage.LOGIN_BUTTON).
       - Comentario: si el proyecto crece, agrupa estos Targets en clases por pantalla/área para claridad.
    */
    public static final Target LOGIN_BUTTON = Target.the("botón de login")
            .located(By.xpath("//input[@id='login-button']"));
}

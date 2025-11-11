package com.saucedemo.stepdefinitions;

import com.saucedemo.model.User;
import com.saucedemo.questions.InventoryPageIsVisible;
import com.saucedemo.questions.ProductsListIsVisible;
import com.saucedemo.questions.LoginErrorIsVisible;
import com.saucedemo.tasks.Login;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class LoginStepDefinitions {

    // Actor: representación del usuario que interactúa con la aplicación.
    // En Screenplay todas las acciones se realizan "como" un Actor.
    private Actor actor;

    // WebDriver controlado por la Ability BrowseTheWeb que se le otorgará al Actor.
    private WebDriver driver;

    // Targets centralizan selectores:
    // - static: comparten un único objeto por clase (no hace falta recrearlos por instancia)
    // - final implícito al no reasignarlos: mantienen inmutabilidad del selector
    private static final Target USERNAME = Target.the("username field").located(By.id("user-name"));
    private static final Target PASSWORD = Target.the("password field").located(By.id("password"));
    private static final Target LOGIN_BUTTON = Target.the("login button").located(By.id("login-button"));

    // Hook @Before: configuración del entorno de ejecución para cada escenario.
    // - Se usa WebDriverManager para evitar manejar chromedriver manualmente.
    // - Se crea un Actor y se le da la Ability BrowseTheWeb.with(driver).
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--headless=new"); // descomenta para headless
        this.driver = new ChromeDriver(options);

        // Crear el actor con un nombre legible en los informes
        this.actor = Actor.named("usuario");

        // Dar la Ability para que el Actor pueda "navegar por la web"
        // BrowseTheWeb.with(driver) encapsula el WebDriver para uso por Screenplay.
        this.actor.can(BrowseTheWeb.with(driver));
    }

    // Hook @After: limpiar recursos tras cada escenario.
    // Importante cerrar el driver para no dejar procesos abiertos.
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Step: abrir la página - aquí usamos driver directamente porque es una acción de setup simple.
    // En Screenplay lo ideal sería encapsular esto en una Task (ej: OpenTheApplication), pero
    // en ejemplos se permite una llamada directa al driver en un Background.
    @Given("que el usuario abre la página de SauceDemo")
    public void que_el_usuario_abre_la_pagina_de_saucedemo() {
        driver.get("https://www.saucedemo.com/");
    }

    // Step combinado (compatibilidad con features antiguos).
    // Usamos Interactions (Enter) para que cada interacción sea registrada y genere capturas
    // cuando serenity.take.screenshots=FOR_EACH_ACTION.
    @When("ingresa su usuario {string} y contraseña {string}")
    public void ingresa_su_usuario_y_contraseña_combinado(String username, String password) {
        actor.attemptsTo(
            Enter.theValue(username).into(USERNAME),
            Enter.theValue(password).into(PASSWORD)
        );
    }

    // Paso separado para username.
    // Comentario: preferir pasos pequeños en features para mejor trazabilidad y screenshots.
    @When("ingresa su usuario {string}")
    public void ingresa_su_usuario(String username) {
        actor.attemptsTo(
            Enter.theValue(username).into(USERNAME)
        );
    }

    // Paso separado para password.
    @When("ingresa su contraseña {string}")
    public void ingresa_su_contraseña(String password) {
        actor.attemptsTo(
            Enter.theValue(password).into(PASSWORD)
        );
    }

    // Paso para click en el botón de login.
    @When("presiona el botón de login")
    public void presiona_el_boton_de_login() {
        actor.attemptsTo(
            Click.on(LOGIN_BUTTON)
        );
    }

    // Then que usa una Question para validar estado.
    // Questions son consultas (lecturas), no realizan acciones.
    @Then("debería ver la página de inventario")
    public void deberia_ver_la_pagina_de_inventario() {
        actor.should(seeThat(InventoryPageIsVisible.isVisible(), is(true)));
    }

    // Verifica la lista de productos usando otra Question reutilizable.
    @Then("los productos deberían estar visibles en la lista")
    public void los_productos_deberían_estar_visibles_en_la_lista() {
        actor.should(seeThat(ProductsListIsVisible.areVisible(), is(true)));
    }

    // Ejemplo: intentar login usando Interactions directas (no Task) para demostrar la diferencia.
    // Aquí mostramos cómo hacer pequeñas Interactions inline.
    @When("intenta iniciar sesión con usuario {string} y contraseña {string}")
    public void intenta_iniciar_sesion_con_usuario_y_contraseña(String username, String password) {
        // Usamos Target.localizados inline para evidenciar que Targets pueden estar en Tasks/Questions o inline.
        actor.attemptsTo(
            Enter.theValue(username).into(Target.the("username field").located(By.id("user-name"))),
            Enter.theValue(password).into(Target.the("password field").located(By.id("password"))),
            Click.on(Target.the("login button").located(By.id("login-button")))
        );
    }

    // Then que comprueba que apareció el mensaje de error de login.
    // Se usa una Question para leer el estado visible del mensaje.
    @Then("debería ver mensaje de error de login")
    public void deberia_ver_mensaje_de_error_de_login() {
        actor.should(seeThat(LoginErrorIsVisible.isVisible(), is(true)));
    }

    // Ejemplo: reintento usando la Task Login.with(user) para mostrar encapsulación.
    // Por qué usar Task aquí:
    // - Tasks agrupan Interactions y representan comportamientos completos (ej: "hacer login").
    // - Facilitan reutilización y hacen los steps legibles: actor.attemptsTo(Login.with(user));
    @When("intenta iniciar sesión con usuario {string} y contraseña {string} usando la tarea de Login")
    public void intenta_iniciar_sesion_con_usuario_y_contraseña_usando_la_tarea(String username, String password) {
        actor.attemptsTo(
            Login.with(new User(username, password))
        );
    }
}

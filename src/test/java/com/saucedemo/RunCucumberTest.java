package com.saucedemo;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

/*
  Runner de Cucumber + Serenity
/

/* -------------------------------------------------------------
   @RunWith(CucumberWithSerenity.class)
   -------------------------------------------------------------
   - Indica a JUnit que ejecute las pruebas usando el runner de Serenity para Cucumber.
   - CucumberWithSerenity integra el reporting de Serenity con la ejecución de Cucumber,
     permitiendo generar los informes HTML automáticamente al terminar.
*/

/* -------------------------------------------------------------
   @CucumberOptions(...)
   -------------------------------------------------------------
   - features: ruta donde están los archivos .feature. Mantenerlos en src/test/resources/features
     facilita que Maven/IDE los descubran.
   - glue: paquete(s) donde están las Step Definitions; debe coincidir con el package de tus stepdefs.
   - plugin: configuraciones de salida de Cucumber; "pretty" mejora la lectura en consola.
   - Puedes añadir otros plugins (por ejemplo json, html) si necesitas outputs adicionales.
*/
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.saucedemo.stepdefinitions",
    plugin = { "pretty" }
)
/* -------------------------------------------------------------
   Clase vacía: ¿por qué no tiene contenido?
   -------------------------------------------------------------
   - El runner no necesita métodos; la anotación es suficiente.
   - JUnit/Cucumber buscan esta clase para arrancar la ejecución de los features declarados.
   - Mantenerla simple facilita identificar el entry-point de los tests.
*/
public class RunCucumberTest { }
package com.saucedemo.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.List;

/*
  Question: ProductsListIsVisible

  Comentarios por secciones (pequeños bloques explicativos junto al código):
  - Purpose:
    * Esta Question comprueba si hay elementos de producto visibles en la página de inventario.
    * En Screenplay, las Questions "leen" el estado de la aplicación y devuelven valores
      que se usan en aserciones (actor.should(seeThat(...))).
*/
public class ProductsListIsVisible implements Question<Boolean> {

    /* Target estático: por qué static final
       - Centraliza el selector CSS de los items (única fuente de verdad).
       - static: se comparte entre instancias para evitar recrearlo.
       - final: el Target no cambia en tiempo de ejecución.
       - Beneficio: mantenimiento más sencillo si cambia el DOM.
    */
    private static final Target PRODUCT_ITEMS =
            Target.the("product items").located(By.cssSelector(".inventory_item"));

    /* answeredBy(Actor actor)
       - Método obligatorio de Question<Boolean>. Aquí se implementa la lógica de lectura.
       - Se recibe el Actor que tiene la Ability BrowseTheWeb; resolveAllFor usa esa Ability.
       - No realiza acciones que muten la aplicación, solo inspecciona elementos.
       - Uso de try/catch: hace la Question robusta frente a cambios o ausencias del elemento.
       - Ejemplo conceptual: devuelve true si existe al menos un item visible.
    */
    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            List<WebElementFacade> items = PRODUCT_ITEMS.resolveAllFor(actor);
            return items != null && !items.isEmpty() && items.get(0).isVisible();
        } catch (Exception e) {
            // Si hay cualquier error técnico (no encontrado, stale element, etc.) devolvemos false.
            return false;
        }
    }

    /* Factory method areVisible()
       - Mejora la lectura en los Steps: actor.should(seeThat(ProductsListIsVisible.areVisible(), is(true)));
       - Evita usar new directamente en los step definitions.
       - Patrón idiomático en Screenplay: Questions con factory methods estáticos para fluidez.
    */
    public static ProductsListIsVisible areVisible() {
        return new ProductsListIsVisible();
    }
}
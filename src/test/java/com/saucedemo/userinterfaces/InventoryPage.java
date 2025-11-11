package com.saucedemo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class InventoryPage {
    public static final Target INVENTORY_CONTAINER = Target.the("contenedor principal del inventario")
            .located(By.xpath("//div[@class='inventory_list']"));
    public static final Target INVENTORY_ITEMS = Target.the("productos listados en el inventario")
            .located(By.xpath("//div[@class='inventory_item']"));
}

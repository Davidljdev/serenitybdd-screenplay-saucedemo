Feature: Login de usuario en SauceDemo

  Como usuario estándar de SauceDemo
  Quiero iniciar sesión en la aplicación
  Para poder ver la página de inventario con los productos listados

  Background:
    Given que el usuario abre la página de SauceDemo

  Scenario: Ingreso exitoso con credenciales válidas
    When ingresa su usuario "standard_user"
    And ingresa su contraseña "secret_sauce"
    And presiona el botón de login
    Then debería ver la página de inventario
    And los productos deberían estar visibles en la lista

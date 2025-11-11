Feature: Reintento de login usando Task

  Como demostración de Screenplay
  Quiero mostrar el uso de una Task reutilizable para login
  Para explicar cómo encapsular acciones y reusarlas

  Background:
    Given que el usuario abre la página de SauceDemo

  Scenario: Intento fallido y reintento exitoso usando la Task Login
    When intenta iniciar sesión con usuario "locked_out_user" y contraseña "secret_sauce"
    Then debería ver mensaje de error de login
    When intenta iniciar sesión con usuario "standard_user" y contraseña "secret_sauce" usando la tarea de Login
    Then debería ver la página de inventario
    And los productos deberían estar visibles en la lista
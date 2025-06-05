Feature: Inicio de sesión

  Como usuario registrado
  Quiero poder iniciar sesión en la plataforma
  Para acceder a mis servicios personalizados

  Scenario: Inicio de sesión exitoso con credenciales válidas
    Given que el usuario tiene una cuenta registrada
    When ingresa su correo electrónico y contraseña correctos
    Then el sistema debe permitirle el acceso y redirigirlo al panel principal

  Scenario: Error al iniciar sesión con credenciales incorrectas
    Given que el usuario tiene una cuenta registrada
    When ingresa una contraseña incorrecta
    Then el sistema debe mostrar un mensaje de error indicando que las credenciales son inválidas

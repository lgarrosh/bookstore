Feature: Установка webhook

  Scenario: Успешная установка webhook
    When отправляем запрос на получение информации по webhook
    Then проверяем что webhook установлен
@reqRes
  Feature: Тестирование API на примере создания объекта
    Scenario: Считывание данных из файла, отправка запроса с измененными данными
      When Считываем данные из json-файла
      Then Изменяем данные в json-объекте
      And Отправляем запрос и проверяем корректность данных

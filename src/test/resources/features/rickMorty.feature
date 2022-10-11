@all
Feature: Поиск информации о персонаже Morty Smith, его последнем эпизоде, последнем персонаже из этого эпизода, сравнение персонажей

  Background:
    Given  Установить базовый url  "https://rickandmortyapi.com/api/"
    Given  Установить header  "Content-type", "application/json"

  Scenario: Находим информацию о персонаже Morty Smith
  Given Передать параметры запроса для персонажа "Morty Smith"



Feature: Weather API

  Background: User has an api key
   # Given generate api key

  Scenario: Get current weather API successful response
    Given request with parameters
      | resourceType | key   | value                                          |
      | parameter    | q     | dallas                                         |
      | parameter    | appid | secret                                         |
      | uri          |       | http://api.openweathermap.org/data/2.5/weather |
    When api is triggered with 'get' method
    Then status code should be 200


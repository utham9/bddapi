Feature: Weather API

  Background: User has an api key
   # Given generate api key

  Scenario: Get current weather API successful response
    Given request with parameters
      | resourceType | key   | value                            |
      | parameter    | q     | dallas                           |
      | parameter    | appid | 7061b46c3acf05aede1a2af07dc07898 |
      | uri          |       | $environment$weather.url         |
    When api is triggered with 'get' method
    Then status code should be 200
    Then response should contain
      | resourceType | key                    | value                      |
      | header       | X-Cache-Key            | /data/2.5/weather?q=dallas |
      | jsonPath     | weather.main[0]        | Clouds                     |
      | jsonPath     | weather.description[0] | overcast clouds            |
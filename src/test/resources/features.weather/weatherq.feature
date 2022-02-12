Feature: Weather -2 API

  Background: User has an api key
   # Given generate api key
  @smoke
  Scenario Outline: Get current weather
    Given request with parameters
      | resourceType | key   | value                            |
      | parameter    | q     | <city>                           |
      | parameter    | appid | 7061b46c3acf05aede1a2af07dc07898 |
      | uri          |       | $environment$weather.url         |
    When api 'weather' is triggered with 'get' method
    Then status code should be <statuscode>
    Then response should contain
      | resourceType | key                    | value                      |
      | header       | X-Cache-Key            | /data/2.5/weather?q=dallas |
      | jsonPath     | weather.main[0]        | <weatherMain>              |
      | jsonPath     | weather.description[0] | <weatherDescription>       |

    Examples:
      | statuscode | city   | weatherMain | weatherDescription |
      | 200        | dallas | Clouds      | overcast clouds    |
      | 500        | dallas | Clouds      | overcast clouds    |

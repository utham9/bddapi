import com.example.qa.api.rest.RestClient;

public class StandaloneRestTest {

  // @Test
  public void currentWeather() {
    RestClient.getInstance()
        .given()
        .param("q", "Dallas")
        .param("appid", "7061b46c3acf05aede1a2af07dc07898")
        .baseUri("http://api.openweathermap.org/data/2.5/weather")
        .get()
        .then()
        .statusCode(200);
  }
}

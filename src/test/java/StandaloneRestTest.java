import com.example.qa.api.rest.RestClient;
import org.junit.Test;

public class StandaloneRestTest {

    @Test
    public void currentWeather() {
        RestClient.given().param("q", "Dallas").param("appid", "7061b46c3acf05aede1a2af07dc07898")
                .baseUri("http://api.openweathermap.org/data/2.5/weather").get().then().statusCode(200);

    }
}

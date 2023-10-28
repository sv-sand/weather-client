import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class NegativeTest extends WeatherClientTester {

    private void wrongCityCheck() {
        try {
            client.loadWeatherToday();
        } catch (WeatherException e) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void emptyApiId() {
        WeatherClient wrongClient = new WeatherClient();

        try {
            wrongClient.loadWeatherToday();
        } catch (WeatherException e) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void emptyCity() {
        wrongCityCheck();
    }

    @Test
    public void wrongCity() {
        client.setCity("Moskow");
        wrongCityCheck();
    }

    @Test
    public void wrongCityEn() {
        setLocale(new Locale("en"));
        client.setCity("Moskow");
        wrongCityCheck();
    }

    @Test
    public void wrongCityRu() {
        setLocale(new Locale("ru"));
        client.setCity("Масква");
        wrongCityCheck();
    }

}

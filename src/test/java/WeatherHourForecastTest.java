import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.objects.WeatherHourForecast;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class WeatherHourForecastTest extends WeatherClientTester {

    @Test
    public void hourForecast() {
        var city = "Moscow";
        client.setCity(city);
        WeatherHourForecast weather = loadWeatherHourForecast();
        checkCity(weather, city);
    }

    @Test
    public void hourForecastEn() {
        var city = "Moscow";
        setLocale(Locale.ENGLISH);
        client.setCity(city);
        WeatherHourForecast weather = loadWeatherHourForecast();
        checkCity(weather, city);
    }

    @Test
    public void hourForecastRu() {
        var city = "Москва";
        setLocale(new Locale("ru"));
        client.setCity(city);

        WeatherHourForecast weather = loadWeatherHourForecast();
        checkCity(weather, city);
    }

    private void checkCity(WeatherHourForecast weather, String city) {
        if (weather.isEmpty())
            Assertions.fail("Weather is empty");

        Assertions.assertEquals(city, weather.getCity().getName());
    }
}

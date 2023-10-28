import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.objects.WeatherToday;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class WeatherTodayTest extends WeatherClientTester {

    @Test
    public void today() {
        var city = "Moscow";
        client.setCity(city);

        WeatherToday weather = loadWeatherToday();
        checkCity(weather, city);
    }

    @Test
    public void todayEn() {
        var city = "Moscow";
        setLocale(Locale.ENGLISH);
        client.setCity(city);

        WeatherToday weather = loadWeatherToday();
        checkCity(weather, city);
    }

    @Test
    public void todayRu() {
        var city = "Москва";
        setLocale(new Locale("ru"));
        client.setCity(city);

        WeatherToday weather = loadWeatherToday();
        checkCity(weather, city);
    }

    private void checkCity(WeatherToday weather, String city) {
        if (weather.isEmpty())
            Assertions.fail("Weather is empty");

        Assertions.assertEquals(city, weather.getCity().getName());
    }
}

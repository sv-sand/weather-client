import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherDailyForecast;
import ru.sanddev.WeatherClient.objects.WeatherHourForecast;
import ru.sanddev.WeatherClient.objects.WeatherToday;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 23.10.2022
 */

public class WeatherClientTest {
    private static final String CONFIG_FILE = "config/app.properties";

    WeatherClient client;
    Properties config;

    public WeatherClientTest() {
        config = new Properties();

        try (InputStream stream = new FileInputStream(CONFIG_FILE)) {
            config.load(stream);
        } catch (IOException e) {
            Assertions.fail(
                    String.format("Attention! Can't find config file. Please create file ./%s according instruction by this program!", CONFIG_FILE)
            );
            return;
        }
        client = new WeatherClient(config.getProperty("apiId"));
    }

    // Today

    protected void todayCheck(String city) {
        WeatherToday weather;
        try {
            weather = client.loadWeatherToday();
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
            return;
        }
        System.out.println(weather.toString());

        if (weather.isEmpty())
            Assertions.fail("Weather is empty");

        if (!weather.getCity().getName().equals(city))
            Assertions.fail("Wrong city name");
    }

    @Test
    public void today() {
        var city = "Moscow";
        client.setCity(city);
        todayCheck(city);
    }

    @Test
    public void todayEn() {
        var city = "Moscow";
        setLocale(Locale.ENGLISH);
        client.setCity(city);
        todayCheck(city);
    }

    @Test
    public void todayRu() {
        var city = "Москва";
        setLocale(new Locale("ru"));
        client.setCity(city);
        todayCheck(city);
    }

    // Hour forecast

    protected void hourForecastCheck(String city) {
        WeatherHourForecast weather;
        try {
            weather = client.loadWeatherHourForecast();
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
            return;
        }

        System.out.println(weather.toString());

        if (weather.isEmpty())
            Assertions.fail("Weather is empty");

        if (!weather.getCity().getName().equals(city))
            Assertions.fail("Wrong city name");
    }

    @Test
    public void hourForecast() {
        var city = "Moscow";
        client.setCity(city);
        hourForecastCheck(city);
    }

    @Test
    public void hourForecastEn() {
        var city = "Moscow";
        setLocale(Locale.ENGLISH);
        client.setCity(city);
        hourForecastCheck(city);
    }

    @Test
    public void hourForecastRu() {
        var city = "Москва";
        setLocale(new Locale("ru"));
        client.setCity(city);
        hourForecastCheck(city);
    }

    // Daily forecast

    protected void dailyForecastCheck(String city) {
        WeatherDailyForecast weather;
        try {
            weather = client.loadWeatherDailyForecast();
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
            return;
        }
        System.out.println(weather.toString());

        if (weather.isEmpty())
            Assertions.fail("Weather is empty");

        if (!weather.getCity().getName().equals(city))
            Assertions.fail("Wrong city name");
    }

    @Disabled("Do need paid account Open weather")
    @Test
    public void dailyForecast() {
        var city = "Moscow";
        client.setCity(city);
        dailyForecastCheck(city);
    }

    @Disabled("Do need paid account Open weather")
    @Test
    public void dailyForecastEn() {
        var city = "Moscow";
        setLocale(new Locale("en"));
        client.setCity(city);
        dailyForecastCheck(city);
    }

    @Disabled("Do need paid account Open weather")
    @Test
    public void dailyForecastRu() {
        var city = "Москва";
        setLocale(new Locale("ru"));
        client.setCity(city);
        dailyForecastCheck(city);
    }

    // Language

    private void setLocale(Locale locale) {
        try {
            client.setLocale(locale);
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void changeLanguage() {
        setLocale(new Locale("en"));
        setLocale(new Locale("ru"));
    }

    @Test
    public void wrongLanguage() {
        try {
            client.setLocale(Locale.ITALY);
        } catch (WeatherException e) {
            return;
        }
        Assertions.fail();
    }

    // Errors

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

    private void wrongCityCheck() {
        try {
            client.loadWeatherToday();
        } catch (WeatherException e) {
            return;
        }
        Assertions.fail();
    }

}

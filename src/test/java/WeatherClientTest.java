import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Assertions;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherHourForecast;
import ru.sanddev.WeatherClient.objects.WeatherToday;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 23.10.2022
 */

@Log4j
public class WeatherClientTest {
    private static final String CONFIG_FILE = "config.properties";

    protected WeatherClient client;
    protected Properties config;

    public WeatherClientTest() {
        config = loadConfig();
        client = new WeatherClient(config.getProperty("apiId"));
    }

    private Properties loadConfig() {
        Properties properties = new Properties();

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(stream);
        } catch (IOException e) {
            String text = String.format("Attention! Can't find config file. Please create file ./%s according instruction by this program!", CONFIG_FILE);
            throw new RuntimeException(text, e);
        }

        return properties;
    }

    protected void setLocale(Locale locale) {
        try {
            client.setLocale(locale);
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
        }
    }

    protected WeatherToday loadWeatherToday() {
        WeatherToday weather;
        try {
            weather = client.loadWeatherToday();
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
            return new WeatherToday();
        }
        log.info(weather.toString());
        return weather;
    }

    protected WeatherHourForecast loadWeatherHourForecast() {
        WeatherHourForecast weather;
        try {
            weather = client.loadWeatherHourForecast();
        } catch (WeatherException e) {
            Assertions.fail(e.getLocalizedMessage());
            weather = new WeatherHourForecast();
        }
        log.info(weather.toString());
        return weather;
    }

}

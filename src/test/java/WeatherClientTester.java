import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.HttpService;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherHourForecast;
import ru.sanddev.WeatherClient.objects.WeatherToday;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 04.07.2023
 */

@Log4j
public class WeatherClientTester {

    protected WeatherClient client;
    private final ResourceBundle mockedRequests;

    public WeatherClientTester() {
        client = new WeatherClient("id");
        mockedRequests = ResourceBundle.getBundle("mocked-requests");
        mockWeatherService();
    }

    private void mockWeatherService() {
        HttpService service = Mockito.mock(HttpService.class);

        addMockRule(service, "TodayEnUrl", "TodayEnResult");
        addMockRule(service, "TodayRuUrl", "TodayRuResult");
        addMockRule(service, "TodayEnWrongCityUrl", "TodayEnWrongCityResult");
        addMockRule(service, "TodayRuWrongCityUrl", "TodayRuWrongCityResult");
        addMockRule(service, "ForecastEnUrl", "ForecastEnResult");
        addMockRule(service, "ForecastRuUrl", "ForecastRuResult");

        replaceServiceInWeatherClient(service);
    }

    private void addMockRule(HttpService service, String urlKey, String resultKey) {
        String url = mockedRequests.getString(urlKey);
        String result = mockedRequests.getString(resultKey);

        try {
            Mockito.when(service.doGetRequest(url))
                    .thenReturn(result);
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    private void replaceServiceInWeatherClient(HttpService service) {
        Field field = ReflectionUtils.findFields(
                WeatherClient.class,
                f -> f.getName().equals("httpService"),
                ReflectionUtils.HierarchyTraversalMode.TOP_DOWN
        ).get(0);

        field.setAccessible(true);

        try {
            field.set(client, service);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;
import ru.sanddev.WeatherClient.HttpService;
import ru.sanddev.WeatherClient.WeatherClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 04.07.2023
 */

public class WeatherClientMockedTest extends WeatherClientTest{

    private final ResourceBundle mockedRequests;

    public WeatherClientMockedTest() {
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
}

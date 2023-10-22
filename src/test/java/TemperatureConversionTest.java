import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.objects.TemperatureUnits;
import ru.sanddev.WeatherClient.objects.WeatherToday;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class TemperatureConversionTest extends WeatherClientMockedTest {
    @Test
    public void tempConversionKelvinToCelsius() {
        final double CELSIUS_TO_KELVIN = 273.15;

        var city = "Moscow";
        client.setCity(city);
        client.setTempUnits(TemperatureUnits.KELVIN);

        WeatherToday weather = loadWeatherToday();

        double expected = weather.getMain().getTemp() - CELSIUS_TO_KELVIN;

        weather.convertTemperatureUnits(TemperatureUnits.CELSIUS);
        Assertions.assertEquals(expected, weather.getMain().getTemp(), 0.001d);
    }

    @Test
    public void tempConversionCelsiusToKelvin() {
        final double CELSIUS_TO_KELVIN = 273.15;

        var city = "Moscow";
        client.setCity(city);
        client.setTempUnits(TemperatureUnits.CELSIUS);

        WeatherToday weather = loadWeatherToday();

        double expected = weather.getMain().getTemp() + CELSIUS_TO_KELVIN;

        weather.convertTemperatureUnits(TemperatureUnits.KELVIN);
        Assertions.assertEquals(expected, weather.getMain().getTemp(), 0.001d);
    }

}

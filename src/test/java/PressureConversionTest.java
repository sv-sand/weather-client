import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.objects.PressureUnits;
import ru.sanddev.WeatherClient.objects.WeatherToday;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class PressureConversionTest extends WeatherClientTester {
    @Test
    public void pressureConversionPascalToBar() {
        final double BAR_TO_PASCAL = 100000;

        var city = "Moscow";
        client.setCity(city);
        client.setPressureUnits(PressureUnits.PASCAL);

        WeatherToday weather = loadWeatherToday();

        double expected = weather.getMain().getPressure() / BAR_TO_PASCAL;

        weather.convertPressureUnits(PressureUnits.BAR);
        Assertions.assertEquals(expected , weather.getMain().getPressure(), 0.001d);
    }

    @Test
    public void pressureConversionPascalToMmHg() {
        final double MHP_TO_PASCAL = 133.3223684;

        var city = "Moscow";
        client.setCity(city);
        client.setPressureUnits(PressureUnits.PASCAL);

        WeatherToday weather = loadWeatherToday();

        double expected = weather.getMain().getPressure() / MHP_TO_PASCAL;

        weather.convertPressureUnits(PressureUnits.MmHg);
        Assertions.assertEquals(expected, weather.getMain().getPressure(), 0.001d);
    }

    @Test
    public void pressureConversionBarToMmHg() {
        final double MmHg_TO_BAR = 133.3223684 / 100000;

        var city = "Moscow";
        client.setCity(city);
        client.setPressureUnits(PressureUnits.BAR);

        WeatherToday weather = loadWeatherToday();

        double expected = weather.getMain().getPressure() / MmHg_TO_BAR;

        weather.convertPressureUnits(PressureUnits.MmHg);
        Assertions.assertEquals(expected, weather.getMain().getPressure(), 0.001d);
    }

}

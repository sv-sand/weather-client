package ru.sanddev.WeatherClient.objects;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.objects.nested.CityData;
import ru.sanddev.WeatherClient.objects.nested.HourForecastListPositionData;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 01.05.2023
 */

@Log4j
@Data
public class WeatherHourForecast extends WeatherData {

    private CityData city;

    private Set<HourForecastListPositionData> list;

    public WeatherHourForecast() {
        list = new HashSet<>();
    }

    // Methods

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void convertTemperatureUnits(TemperatureUnits targetTempUnits) {
        for (var item: list) {
            log.debug("Prepare temperature conversion by " + item.getDate());
            item.getMain().convertTemperature(targetTempUnits);
        }
    }

    @Override
    public void convertPressureUnits(PressureUnits targetPressureUnits) {
        for (var item: list) {
            log.debug("Prepare pressure conversion by " + item.getDate());
            item.getMain().convertPressure(targetPressureUnits);
        }
    }
}

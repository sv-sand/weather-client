package ru.sanddev.WeatherClient.objects;

import lombok.Data;
import ru.sanddev.WeatherClient.objects.nested.*;

import java.util.Date;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 15.10.2022
 */

@Data
public class WeatherToday extends WeatherData{

    private CityData city;

    // Time of data calculation, unix, UTC
    private Date date;

    // Shift in hours from UTC
    private long timezone;

    // System data
    private SystemData sys;

    // Coordinates
    private CoordinatesData coord;

    // More info Weather condition codes
    private DescriptionData weather;

    // Internal parameter
    private String base;

    // General parameters
    private MainData main;

    // Visibility, meter. The maximum value of the visibility is 10km
    private long visibility;

    // Wind info
    private WindData wind;

    // Cloudiness, % ("all" = 100)
    private double clouds;

    // Rain volume for the last hours, mm ("1h" = 3.16)
    private RainData rain;

    // Snow volume for the last hours, mm ("1h" = 3.16)
    private SnowData snow;

    // Methods

    @Override
    public boolean isEmpty() {
        return date == null;
    }

    @Override
    public void convertTemperatureUnits(TemperatureUnits targetTempUnits) {
        getMain().convertTemperature(targetTempUnits);
    }

    @Override
    public void convertPressureUnits(PressureUnits targetPressureUnits) {
        getMain().convertPressure(targetPressureUnits);
    }
}

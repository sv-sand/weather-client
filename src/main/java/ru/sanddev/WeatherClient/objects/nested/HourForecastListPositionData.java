package ru.sanddev.WeatherClient.objects.nested;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.Date;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 01.05.2023
 */

@Log4j
@Data
public class HourForecastListPositionData {

    // Time of data forecasted
    private Date date;

    // General parameters
    private MainData main;

    // More info Weather condition codes
    private DescriptionData weather;

    // Cloudiness, % ("all" = 100)
    private double clouds;

    // Wind info
    private WindData wind;

    // Visibility, meter. The maximum value of the visibility is 10km
    private long visibility;

    // Precipitation volume, mm
    private double pop;

    // Rain volume for the last hours, mm ("1h" = 3.16)
    private RainData rain;

    // Snow volume for the last hours, mm ("1h" = 3.16)
    private SnowData snow;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return date == null;
    }
}

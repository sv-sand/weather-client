package ru.sanddev.WeatherClient.objects.nested;

import lombok.Data;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 29.04.2023
 */

@Data
public class WindData {

    // Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    private double speed;

    // Wind direction, degrees (meteorological)
    private long deg;

    // Wind gust. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour
    private double gust;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return speed == 0 && deg == 0 && gust == 0;
    }
}

package ru.sanddev.WeatherClient.objects.nested;

import lombok.Data;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 29.04.2023
 */

@Data
public class CoordinatesData {

    // Latitude
    private double lat;

    // Longitude
    private double lon;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return lat ==0 && lon == 0;
    }
}

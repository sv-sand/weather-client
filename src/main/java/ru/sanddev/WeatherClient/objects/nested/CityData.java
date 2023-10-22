package ru.sanddev.WeatherClient.objects.nested;

import lombok.Data;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 29.04.2023
 */

@Data
public class CityData {

    // City ID. Please note that built-in geocoder functionality has been deprecated.
    private long id;

    // City name. Please note that built-in geocoder functionality has been deprecated.
    private String name = "";

    // Coordinates
    private CoordinatesData coord;

    // Country code (GB, JP etc.). Please note that built-in geocoder functionality has been deprecated.
    private String country = "";

    // Internal parameter
    private long population;

    // Shift in hours from UTC
    private long timezone;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return name.isEmpty();
    }
}

package ru.sanddev.WeatherClient.objects.nested;

import lombok.Data;

import java.util.Date;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 29.04.2023
 */

@Data
public class SystemData {

    // Country code (GB, JP etc.)
    private String country = "";

    // Sunrise time, unix, UTC
    private Date sunrise;

    // Sunset time, unix, UTC
    private Date sunset;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return country.isEmpty() && sunrise == null && sunset == null;
    }
}

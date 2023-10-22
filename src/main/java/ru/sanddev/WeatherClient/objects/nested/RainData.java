package ru.sanddev.WeatherClient.objects.nested;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 30.04.2023
 */

@Data
public class RainData {
    // Rain volume for the last 1 hour, mm
    @SerializedName("1h")
    private double h1;

    // Rain volume for the last 3 hour, mm
    @SerializedName("3h")
    private double h3;

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return h1 == 0 && h3 == 0;
    }
}

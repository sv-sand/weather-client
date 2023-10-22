package ru.sanddev.WeatherClient.objects.nested;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.objects.PressureUnits;
import ru.sanddev.WeatherClient.objects.TemperatureUnits;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 29.04.2023
 */

@Data
@Log4j
public class MainData {

    // Current temperature units
    private TemperatureUnits tempUnits;

    // Current pressure units
    private PressureUnits pressureUnits;

    // Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    private double temp;

    // Temperature. This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @SerializedName(value = "feels_like")
    private double tempFeels;

    // Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @SerializedName(value = "temp_min")
    private double tempMin;

    // Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @SerializedName(value = "temp_max")
    private double tempMax;

    // Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
    private double pressure;

    // Atmospheric pressure on the sea level, hPa
    @SerializedName(value = "sea_level")
    private double seaLevel;

    // Atmospheric pressure on the ground level, hPa
    @SerializedName(value = "grnd_level")
    private double groundLevel;

    // Humidity, %
    private double humidity;

    // Internal parameter
    @SerializedName(value = "temp_kf")
    private double tempKf;

    public MainData() {
        tempUnits = TemperatureUnits.KELVIN;
        pressureUnits = PressureUnits.PASCAL;
    }

    /**
     * Method convert temperature to another measure unit
     * @param targetTempUnit - new measure unit
     */
    public void convertTemperature(TemperatureUnits targetTempUnit) {
        temp = TemperatureUnits.convert(tempUnits, targetTempUnit, temp);
        tempMin = TemperatureUnits.convert(tempUnits, targetTempUnit, tempMin);
        tempMax = TemperatureUnits.convert(tempUnits, targetTempUnit, tempMax);
        tempFeels = TemperatureUnits.convert(tempUnits, targetTempUnit, tempFeels);

        tempUnits = targetTempUnit;
    }

    /**
     * Method convert pressure to another measure unit
     * @param targetPressureUnit - new measure unit
     */
    public void convertPressure(PressureUnits targetPressureUnit) {
        pressure = PressureUnits.convert(pressureUnits, targetPressureUnit, pressure);
        seaLevel = PressureUnits.convert(pressureUnits, targetPressureUnit, seaLevel);
        groundLevel = PressureUnits.convert(pressureUnits, targetPressureUnit, seaLevel);

        pressureUnits = targetPressureUnit;
    }

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public boolean isEmpty() {
        return temp == 0 && tempFeels == 0 && tempMin == 0 && tempMax == 0 && seaLevel == 0
                && groundLevel == 0 && humidity == 0;
    }
}

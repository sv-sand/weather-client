package ru.sanddev.WeatherClient.objects;

public abstract class WeatherData {

    /**
     * Check empty or filled object
     * @return true - if empty object, false - if object contains any data
     */
    public abstract boolean isEmpty();

    /**
     * Method convert temperature to another measure unit
     * @param targetTempUnit - new measure unit
     */
    public abstract void convertTemperatureUnits(TemperatureUnits targetTempUnit);

    /**
     * Method convert pressure to another measure unit
     * @param targetPressureUnit - new measure unit
     */
    public abstract void convertPressureUnits(PressureUnits targetPressureUnit);
}

package ru.sanddev.WeatherClient.objects;

import lombok.extern.log4j.Log4j;

@Log4j
public enum TemperatureUnits {
    CELSIUS,
    KELVIN;

    private final static double CELSIUS_TO_KELVIN = 273.15;

    // Conversion methods

    public static double convert(TemperatureUnits currentUnit, TemperatureUnits targetUnit, double value) {

        log.info(String.format("Temperature units conversion, current %s, target %s for value %,.3f", currentUnit, targetUnit, value));

        double result;

        switch (currentUnit) {
            case KELVIN:
                switch (targetUnit) {
                    case CELSIUS: result = convertKelvinToCelsius(value); break;
                    default:
                        result = value;
                        log.debug("Do not need conversion temperature units");
                }
                break;

            case CELSIUS:
                switch (targetUnit) {
                    case KELVIN: result = convertCelsiusToKelvin(value); break;
                    default:
                        result = value;
                        log.debug("Do not need conversion temperature units");
                }
                break;

            default:
                result = value;
                log.debug("Do not need conversion temperature units");
        }
        return result;
    }

    private static double convertCelsiusToKelvin(double temp) {
        return temp + CELSIUS_TO_KELVIN;
    }

    private static double convertKelvinToCelsius(double temp) {
        return temp - CELSIUS_TO_KELVIN;
    }
}

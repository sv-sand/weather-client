package ru.sanddev.WeatherClient.objects;

import lombok.extern.log4j.Log4j;

@Log4j
public enum PressureUnits {

    PASCAL,
    MmHg,
    BAR;

    private final static double MHP_TO_PASCAL = 133.3223684;
    private final static double BAR_TO_PASCAL = 100000;
    private final static double MmHg_TO_BAR = MHP_TO_PASCAL / BAR_TO_PASCAL;

    // Conversion methods

    public static double convert(PressureUnits currentUnit, PressureUnits targetUnit, double value) {
        log.info(String.format("Pressure units conversion current %s, target %s for value %,.3f", currentUnit, targetUnit, value));

        double result;

        switch (currentUnit) {
            case PASCAL:
                switch (targetUnit) {
                    case MmHg: result = convertPascalToMmHg(value); break;
                    case BAR: result = convertPascalToBar(value); break;
                    default:
                        result = value;
                        log.debug("Do not need conversion");
                }
                break;

            case BAR:
                switch (targetUnit) {
                    case MmHg: result = convertBarToMmHg(value); break;
                    case PASCAL: result = convertBarToPascal(value); break;
                    default:
                        result = value;
                        log.debug("Do not need conversion");
                }
                break;

            case MmHg:
                switch (targetUnit) {
                    case BAR: result = convertMmHgToBar(value); break;
                    case PASCAL: result = convertMmHgToPascal(value); break;
                    default:
                        result = value;
                        log.debug("Do not need conversion");
                }
                break;

            default:
                result = value;
                log.debug("Do not need conversion");
        }

        return result;
    }

    private static double convertPascalToMmHg(double pressure) {
        return pressure / MHP_TO_PASCAL;
    }

    private static double convertPascalToBar(double pressure) {
        return pressure / BAR_TO_PASCAL;
    }

    private static double convertMmHgToPascal(double pressure) {
        return pressure * MHP_TO_PASCAL;
    }

    private static double convertMmHgToBar(double pressure) {
        return pressure * MmHg_TO_BAR;
    }

    private static double convertBarToPascal(double pressure) {
        return pressure * BAR_TO_PASCAL;
    }

    private static double convertBarToMmHg(double pressure) {
        return pressure / MmHg_TO_BAR;
    }
}

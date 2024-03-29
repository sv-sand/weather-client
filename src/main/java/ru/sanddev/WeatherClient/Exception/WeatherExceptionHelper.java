package ru.sanddev.WeatherClient.Exception;

import lombok.extern.log4j.Log4j;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 01.07.2023
 */

@Log4j
public class WeatherExceptionHelper {

    private final ResourceBundle exceptionBundle;

    public WeatherExceptionHelper(Locale locale) {
        exceptionBundle = ResourceBundle.getBundle("weather-exceptions", locale);
    }

    public void raiseExceptionConnection(Throwable cause, String description) throws WeatherException {
        String msg = exceptionBundle.getString("ErrorHttpConnection");

        WeatherException exception =  new WeatherException(
                String.format(msg, description),
                cause
        );
        log.error(exception.getLocalizedMessage(), exception);

        throw exception;
    }

    public void raiseExceptionApiId(String appId) throws WeatherException {
        String msg = exceptionBundle.getString("ErrorApiId");

        WeatherException exception =  new WeatherException(
                String.format(msg, appId)
        );
        log.error(exception.getLocalizedMessage(), exception);

        throw exception;
    }

    public void raiseExceptionLocale(String langCode) throws WeatherException {
        String msg = exceptionBundle.getString("ErrorLocale");

        WeatherException exception =  new WeatherException(
                String.format(msg, langCode)
        );
        log.error(exception.getLocalizedMessage(), exception);

        throw exception;
    }

    public void raiseExceptionCity(String cityName) throws WeatherException {
        String msg = exceptionBundle.getString("ErrorCity");

        WeatherException exception =  new WeatherException(
                String.format(msg, cityName)
        );
        log.error(exception.getLocalizedMessage(), exception);

        throw exception;
    }

    public void raiseExceptionHttp(Integer responseCode, String description) throws WeatherException {
        String msg = exceptionBundle.getString("ErrorHttp");

        WeatherException exception = new WeatherException(
                String.format(msg, responseCode, description)
        );
        log.error(exception.getLocalizedMessage(), exception);

        throw exception;
    }

}

package ru.sanddev.WeatherClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.Exception.WeatherExceptionHelper;
import ru.sanddev.WeatherClient.json.DailyForecastListPositionDeserializer;
import ru.sanddev.WeatherClient.json.HourForecastListPositionDeserializer;
import ru.sanddev.WeatherClient.json.WeatherHourForecastDeserializer;
import ru.sanddev.WeatherClient.json.WeatherTodayDeserializer;
import ru.sanddev.WeatherClient.json.nested.SystemDataDeserializer;
import ru.sanddev.WeatherClient.objects.*;
import ru.sanddev.WeatherClient.objects.nested.DailyForecastListPosition;
import ru.sanddev.WeatherClient.objects.nested.HourForecastListPosition;
import ru.sanddev.WeatherClient.objects.nested.SystemData;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 16.05.2022
 */

@Log4j
public class WeatherClient {

    public final static TemperatureUnits DEFAULT_TEMPERATURE_UNITS = TemperatureUnits.CELSIUS;
    public final static String URL = "https://api.openweathermap.org/data/2.5";
    public final static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @Getter @Setter
    private String apiId;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private TemperatureUnits tempUnits;

    @Getter
    private Locale locale;

    private WeatherExceptionHelper exceptionHelper;
    private HttpClient httpClient;

    // Constructors

    public WeatherClient() {
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        init();
    }

    public WeatherClient(String apiId) {
        this.apiId = apiId;
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        init();
    }

    public WeatherClient(String apiId, String cityName) {
        this.apiId = apiId;
        this.city = cityName;
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        init();
    }

    public WeatherClient(String apiId, String cityName, TemperatureUnits tempUnits) {
        this.apiId = apiId;
        this.city = cityName;
        this.tempUnits = tempUnits;
        init();
    }

    // Methods

    private void init() {
        log.debug("Weather client initialization");

        locale = DEFAULT_LOCALE;
        exceptionHelper = new WeatherExceptionHelper(locale);
        httpClient = new HttpClient();
    }

    private void checkHttpRequestResult(String jsonString) throws WeatherException {
        log.debug("Request result checking");

        Gson gson = new Gson();
        HttpRequestResult result = gson.fromJson(jsonString, HttpRequestResult.class);

        if (result.getCod() != 200) {
            exceptionHelper.raiseExceptionHttp(result.getCod(), result.getMessage());
        }
    }

    /**
     * Load current weather data from <a href="https://openweathermap.org">https://openweathermap.org</a> API
     * @return weather data object
     */
    public WeatherToday loadWeatherToday() throws WeatherException {
        String url = URL + "/weather?q=" + city + "&appid=" + apiId + "&lang=" + locale.getLanguage();
        String jsonString;

        try {
            jsonString = httpClient.doGetRequest(url);
        } catch (IOException e) {
            exceptionHelper.raiseExceptionConnection(e, e.getLocalizedMessage());
            return new WeatherToday();
        }

        checkHttpRequestResult(jsonString);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherToday.class, new WeatherTodayDeserializer())
                .registerTypeAdapter(SystemData.class, new SystemDataDeserializer())
                .create();

        WeatherToday weather = gson.fromJson(jsonString, WeatherToday.class);
        weather.convertTemperatureUnits(tempUnits);

        return weather;
    }

    /**
     * Load weather hourly forecast data from <a href="https://openweathermap.org">https://openweathermap.org</a> API with 40 time stamp count.
     * It includes weather forecast data with 3-hour step.
     * @return weather hourly forecast data object
     */
    public WeatherHourForecast loadWeatherHourForecast() throws WeatherException {
        return loadWeatherHourForecast(40);
    }

    /**
     * Load weather hourly forecast data from <a href="https://openweathermap.org">https://openweathermap.org</a> API.
     * It includes weather forecast data with 3-hour step.
     * @param timeStampCount a number of timestamps, which will be returned in the API response.
     * @return weather hourly forecast data object
     */
    public WeatherHourForecast loadWeatherHourForecast(int timeStampCount) throws WeatherException {
        String url = URL + "/forecast?q=" + city + "&cnt=" + timeStampCount + "&appid=" + apiId + "&lang=" + locale.getLanguage();
        String jsonString;

        try {
            jsonString = httpClient.doGetRequest(url);
        } catch (IOException e) {
            exceptionHelper.raiseExceptionConnection(e, e.getLocalizedMessage());
            return new WeatherHourForecast();
        }

        checkHttpRequestResult(jsonString);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherHourForecast.class, new WeatherHourForecastDeserializer())
                .registerTypeAdapter(HourForecastListPosition.class, new HourForecastListPositionDeserializer())
                .create();

        WeatherHourForecast weather = gson.fromJson(jsonString, WeatherHourForecast.class);
        weather.convertTemperatureUnits(tempUnits);

        return weather;
    }

    /**
     * Load weather daily forecast data from <a href="https://openweathermap.org">https://openweathermap.org</a> API with 16 days duration<br>
     * <b>IMPORTANT! Do need paid account on <a href="https://openweathermap.org">https://openweathermap.org</a>, if don't have a paid account you will get an error "Invalid API key"</b>
     * @return weather daily forecast data object
     */
    public WeatherDailyForecast loadWeatherDailyForecast() throws WeatherException {
        return loadWeatherDailyForecast(16);
    }

    /**
     * Load weather daily forecast data from <a href="https://openweathermap.org">https://openweathermap.org</a> API.<br>
     * <b>IMPORTANT! Do need paid account on <a href="https://openweathermap.org">https://openweathermap.org</a>, if don't have a paid account you will get an error "Invalid API key"</b>
     * @param timeStampCount a count of days forecast
     * @return weather daily forecast data object
     */
    public WeatherDailyForecast loadWeatherDailyForecast(int timeStampCount) throws WeatherException {
        String url = URL + "/forecast/daily?q=" + city + "&cnt=" + timeStampCount + "&appid=" + apiId + "&lang=" + locale.getLanguage();
        String jsonString;

        try {
            jsonString = httpClient.doGetRequest(url);
        } catch (IOException e) {
            exceptionHelper.raiseExceptionConnection(e, e.getLocalizedMessage());
            return new WeatherDailyForecast();
        }

        checkHttpRequestResult(jsonString);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DailyForecastListPosition.class, new DailyForecastListPositionDeserializer())
                .create();

        WeatherDailyForecast weather = gson.fromJson(jsonString, WeatherDailyForecast.class);
        weather.convertTemperatureUnits(tempUnits);

        return weather;
    }

    // Getters & setters

    /**
     * Change weather client locale
     * @param locale - new locale
     * @throws WeatherException - if locale is not support
     */
    public void setLocale(Locale locale) throws WeatherException {
        log.debug(
                String.format("Language change begin, current %s, target %s", this.locale.getLanguage(), locale.getLanguage())
        );

        if (this.locale == locale) {
            log.debug("Do not need change the language");
            return;
        }

        try {
            LocaleCodes.valueOf(locale.toString());
        } catch (IllegalArgumentException e) {
            exceptionHelper.raiseExceptionLangCode(locale.getLanguage());
            return;
        }

        this.locale = locale;
        exceptionHelper = new WeatherExceptionHelper(locale);

        log.debug("Language was changed");
    }

    // Inner objects

    public enum LocaleCodes {
        en,
        ru
    }
}

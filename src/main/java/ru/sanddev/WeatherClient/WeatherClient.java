package ru.sanddev.WeatherClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.Exception.WeatherExceptionHelper;
import ru.sanddev.WeatherClient.json.HourForecastListPositionDeserializer;
import ru.sanddev.WeatherClient.json.WeatherHourForecastDeserializer;
import ru.sanddev.WeatherClient.json.WeatherTodayDeserializer;
import ru.sanddev.WeatherClient.json.nested.SystemDataDeserializer;
import ru.sanddev.WeatherClient.objects.*;
import ru.sanddev.WeatherClient.objects.nested.HourForecastListPositionData;
import ru.sanddev.WeatherClient.objects.nested.SystemData;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 16.05.2022
 */

@Log4j
public class WeatherClient {

    public final static String URL = "https://api.openweathermap.org/data/2.5";
    public final static TemperatureUnits DEFAULT_TEMPERATURE_UNITS = TemperatureUnits.CELSIUS;
    public final static PressureUnits DEFAULT_PRESSURE_UNITS = PressureUnits.MmHg;
    public final static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @Getter @Setter
    private String apiId;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private TemperatureUnits tempUnits;

    @Getter @Setter
    private PressureUnits pressureUnits;

    @Getter
    private Locale locale;

    private WeatherExceptionHelper exceptionHelper;
    private HttpService httpService;

    public enum LocaleCodes {
        en,
        ru
    }

    // Constructors

    public WeatherClient() {
        this.apiId = "";
        this.city = "";
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        this.pressureUnits = DEFAULT_PRESSURE_UNITS;

        init();
    }

    public WeatherClient(String apiId) {
        this.apiId = apiId;
        this.city = "";
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        this.pressureUnits = DEFAULT_PRESSURE_UNITS;

        init();
    }

    public WeatherClient(String apiId, String cityName) {
        this.apiId = apiId;
        this.city = cityName;
        this.tempUnits = DEFAULT_TEMPERATURE_UNITS;
        this.pressureUnits = DEFAULT_PRESSURE_UNITS;

        init();
    }

    // Methods

    private void init() {
        log.debug("Weather client initialization");

        locale = DEFAULT_LOCALE;
        exceptionHelper = new WeatherExceptionHelper(locale);
        httpService = new HttpService();
    }

    /**
     * Load current weather data from <a href="https://openweathermap.org">https://openweathermap.org</a> API
     * @return weather data object
     */
    public WeatherToday loadWeatherToday() throws WeatherException {
        beforeLoad();

        String url = URL + "/weather?q=" + city + "&appid=" + apiId + "&lang=" + locale.getLanguage();
        String jsonString;

        try {
            jsonString = httpService.doGetRequest(url);
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

        afterLoad(weather);
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
        beforeLoad();

        String url = URL + "/forecast?q=" + city + "&cnt=" + timeStampCount + "&appid=" + apiId + "&lang=" + locale.getLanguage();
        String jsonString;

        try {
            jsonString = httpService.doGetRequest(url);
        } catch (IOException e) {
            exceptionHelper.raiseExceptionConnection(e, e.getLocalizedMessage());
            return new WeatherHourForecast();
        }

        checkHttpRequestResult(jsonString);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherHourForecast.class, new WeatherHourForecastDeserializer())
                .registerTypeAdapter(HourForecastListPositionData.class, new HourForecastListPositionDeserializer())
                .create();

        WeatherHourForecast weather = gson.fromJson(jsonString, WeatherHourForecast.class);

        afterLoad(weather);
        return weather;
    }

    private void checkHttpRequestResult(String jsonString) throws WeatherException {
        log.debug("Request result checking");

        Gson gson = new Gson();
        HttpRequestResult result = gson.fromJson(jsonString, HttpRequestResult.class);

        if (result.getCod() != 200) {
            exceptionHelper.raiseExceptionHttp(result.getCod(), result.getMessage());
        }
    }

    private void beforeLoad()  throws WeatherException {
        if (apiId.equals(""))
            exceptionHelper.raiseExceptionApiId("");
        if (city.equals(""))
            exceptionHelper.raiseExceptionCity("");
    }

    private void afterLoad(WeatherData weather) {
        weather.convertTemperatureUnits(tempUnits);
        weather.convertPressureUnits(pressureUnits);
    }

    // Getters & setters

    /**
     * Change weather client locale
     * @param newLocale - new locale
     * @throws WeatherException - if locale is not support
     */
    public void setLocale(Locale newLocale) throws WeatherException {
        log.debug(
                String.format("Language change begin, current %s, target %s", locale.getLanguage(), newLocale.getLanguage())
        );

        if (locale == newLocale) {
            log.debug("Do not need change the language");
            return;
        }

        try {
            LocaleCodes.valueOf(newLocale.getLanguage());
        } catch (IllegalArgumentException e) {
            exceptionHelper.raiseExceptionLocale(newLocale.toString());
            return;
        }

        locale = newLocale;
        exceptionHelper = new WeatherExceptionHelper(newLocale);

        log.debug("Language was changed");
    }
}

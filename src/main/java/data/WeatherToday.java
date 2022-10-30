package data;

import org.json.simple.JSONObject;
import java.util.Date;

/**
 * @author Sand, sve.snd@gmail.com, http://sanddev.ru
 * @since 15.10.2022
 */

public class WeatherToday {
    private boolean isEmpty;

    private Date date;
    private long timeZone;
    private long visibility;
    private String cityName;

    // Main
    private double temp;

    private double tempMin;
    private double tempMax;
    private double tempFeels;
    private long groundLevel;
    private long seaLevel;
    private long humidity;
    private long pressure;

    // Sys
    private String country;
    private long sunrise;
    private long sunset;

    // Coord
    private double latitude;
    private double longitude;

    // Wind
    private long windDeg;
    private double windSpeed;
    private double windGust;

    public WeatherToday() {
        isEmpty = true;
        date = new Date();
        cityName = "";
        country = "";
    }

    // Getters & setters

    public boolean isEmpty() {
        return isEmpty;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public long getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(long timeZone) {
        this.timeZone = timeZone;
    }

    public long getVisibility() {
        return visibility;
    }
    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public String getCity() {
        return cityName;
    }
    public void setCity(String cityName) {
        this.cityName = cityName;
    }

    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempFeels() {
        return tempFeels;
    }
    public void setTempFeels(double tempFeels) {
        this.tempFeels = tempFeels;
    }

    public long getGroundLevel() {
        return groundLevel;
    }
    public void setGroundLevel(long groundLevel) {
        this.groundLevel = groundLevel;
    }

    public long getSeaLevel() {
        return seaLevel;
    }
    public void setSeaLevel(long seaLevel) {
        this.seaLevel = seaLevel;
    }

    public long getHumidity() {
        return humidity;
    }
    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getPressure() {
        return pressure;
    }
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getWindDeg() {
        return windDeg;
    }
    public void setWindDeg(long windDeg) {
        this.windDeg = windDeg;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }
    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

}

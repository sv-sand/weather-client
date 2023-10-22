package ru.sanddev.WeatherClient.json;

import com.google.gson.*;
import ru.sanddev.WeatherClient.objects.WeatherToday;
import ru.sanddev.WeatherClient.objects.nested.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author Alexander Svetlakov <sve.snd@gmail.com>
 * @since 30.04.2023
 */

public class WeatherTodayDeserializer implements JsonDeserializer<WeatherToday> {
    @Override
    public WeatherToday deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        WeatherToday weather = new WeatherToday();

        var dt = jsonObject.get("dt").getAsLong();
        weather.setDate(new Date(dt * 1000 ));

        CityData city = new CityData();
        city.setName(jsonObject.get("name").getAsString());
        city.setTimezone(jsonObject.get("timezone").getAsLong() / 3600);
        city.setCoord(
                jsonDeserializationContext.deserialize(jsonObject.get("coord"), CoordinatesData.class)
        );
        weather.setCity(city);

        weather.setSys(
                jsonDeserializationContext.deserialize(jsonObject.get("sys"), SystemData.class)
        );

        for (JsonElement obj: jsonObject.get("weather").getAsJsonArray())
            weather.setWeather(
                jsonDeserializationContext.deserialize(obj.getAsJsonObject(), DescriptionData.class)
            );

        weather.setBase(jsonObject.get("base").getAsString());

        weather.setMain(
                jsonDeserializationContext.deserialize(jsonObject.get("main"), MainData.class)
        );

        weather.setVisibility(jsonObject.get("visibility").getAsLong());

        weather.setWind(
                jsonDeserializationContext.deserialize(jsonObject.get("wind"), WindData.class)
        );

        weather.setClouds(
                jsonObject.get("clouds").getAsJsonObject().get("all").getAsDouble()
        );

        weather.setRain(
                jsonDeserializationContext.deserialize(jsonObject.get("rain"), RainData.class)
        );

        weather.setSnow(
                jsonDeserializationContext.deserialize(jsonObject.get("snow"), SnowData.class)
        );

        return weather;
    }
}

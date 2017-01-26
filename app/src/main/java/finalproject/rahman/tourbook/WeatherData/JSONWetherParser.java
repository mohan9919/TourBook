package finalproject.rahman.tourbook.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import Model.Place;
//import Model.Weather;
//import Util.Utils;
import finalproject.rahman.tourbook.WeatherModel.Place;
import finalproject.rahman.tourbook.WeatherModel.Weather;
import finalproject.rahman.tourbook.WeatherUtils.Utils;

/**
 * Created by rahma on 12/19/2016.
 */

public class JSONWetherParser {
    public static Weather getWeather(String data)
    {
        Weather weather=new Weather();
        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place=new Place();

            JSONObject coorObj= Utils.getObject("coord",jsonObject);
            place.setLat(Utils.getFloat("lat",coorObj));
            place.setLon(Utils.getFloat("lon",coorObj));

            JSONObject sysObj=Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            place.setLastUpdates(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place=place;

            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject jsonWeather=jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));
            //weather.currentCondition.setIcon(Utils.getString(""));

            JSONObject mainObj=Utils.getObject("main",jsonObject);
            weather.currentCondition.setTemperature(Utils.getDouble("temp",mainObj));
            weather.currentCondition.setHumidity(Utils.getInt("humidity",mainObj));
            weather.currentCondition.setPresure(Utils.getInt("pressure",mainObj));
            weather.currentCondition.setMaxTemp(Utils.getInt("temp_max",mainObj));
            weather.currentCondition.setMinTemp(Utils.getInt("temp_min",mainObj));

            //weather.currentCondition.set(Utils.getDouble("temp",mainObj));

            JSONObject windObj=Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObj));
            weather.wind.setDeg(Utils.getFloat("deg",windObj));

            JSONObject cloudObj=Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package org.example.handle.weather;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseWeather {
    private static final String API_KEY;

    static {
        Properties prop = new Properties();
        try {
            prop.load(ParseWeather.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        API_KEY = prop.getProperty("weatherApiKey");
        if (API_KEY == null) {
            throw new RuntimeException(); //TODO: придумать что нибудь получше
        }
    }
    
    public static String getWeather(String city) {
        return getWeatherData(city).toString();
    }

    private static WeatherData getWeatherData(String city) {
        WeatherData data = new WeatherData();
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            JSONObject json = new JSONObject(response.toString());
            data.setTemperature(json.getJSONObject("main").getDouble("temp"));
            data.setHumidity(json.getJSONObject("main").getDouble("humidity"));
            data.setPressure(json.getJSONObject("main").getDouble("pressure"));
            data.setWeather(json.getJSONArray("weather").getJSONObject(0).getString("main"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
package edu.iastate.netid.thingslab;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * This class represents the response
 * received from the OpenWeatherMap API when querying
 * by zip code.
 */
public class ZipResponse implements Serializable {
    private class Coord implements Serializable {
        double lon = 0.0;
        double lat = 0.0;
    }
    private Coord coord = new Coord();

    private class Weather implements Serializable {
        int id = 0;
        String main = "";
        String description = "";
        String icon = "";
    }
    private Weather[] weather = new Weather[1];

    private String base = "";

    private class Main implements Serializable {
        double temp = 0.0;
        int pressure = 0;
        int humidity = 0;
        double temp_min = 0.0;
        double temp_max = 0.0;
    }
    private Main main = new Main();

    private int visibility = 0;

    private class Wind implements Serializable {
        double speed = 0.0;
        double deg = 0;
    }
    private Wind wind = new Wind();

    private class Clouds implements Serializable {
        int all = 0;
    }
    private Clouds clouds = new Clouds();

    private long dt = 0;

    private class Sys implements Serializable {
        int type = 0;
        int id = 0;
        double message = 0.0;
        String country = "";
        long sunrise = 0;
        long sunset = 0;
    }
    private Sys sys = new Sys();

    private int timezone = 0;

    private long id = 0;

    private String name = "";

    private int cod = 0;

    public static String getURLForZip(int zipCode) {
        /* This uses Mat's API key... get your own at openweathermap.org if you want! */
        return "https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us&APPID=bf709bec54114a63f546e935fbd62921";
    }

    /**
     * Parses a JSON string (the response from a zip code query of the
     * OpenWeatherMap API) into a ZipResponse object.
     * @param json The JSON string to parse.
     * @return The ZipResponse object built from the string
     */
    public static ZipResponse getZipResponseFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ZipResponse.class);
    }

    public double getTemperature() {
        return main.temp - 273.1;
    }

    public String getDescription() {
        return weather[0].main;
    }

    public String getDetailedDescription() {
        return weather[0].description;
    }

    public int getPressure() {
        return main.pressure;
    }

    public int getHumidity() {
        return main.humidity;
    }

    /**
     * Gets the wind speed in meters per second.
     * @return The wind speed in meters per second.
     */
    public double getWindSpeedMPerSec() {
        return wind.speed;
    }

    /**
     * Gets the wind direction of this ZipResponse.
     * @return a String - "North", "South", "East", or "West".
     * Returns "Unknown" if
     */
    public String getWindDirection() {
        if(wind.deg <= 45 || wind.deg > 360 - 45 ) {
            return "North";
        } else if(wind.deg <= 90 + 45) {
            return "East";
        } else if(wind.deg < 180 + 45) {
            return "South";
        } else if(wind.deg < 270 + 45){
            return "West";
        } else {
            return "Unknown";
        }
    }
}

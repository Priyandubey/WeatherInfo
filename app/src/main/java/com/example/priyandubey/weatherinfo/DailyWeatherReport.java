package com.example.priyandubey.weatherinfo;

public class DailyWeatherReport {

    public String city;
    public String country;
    public int currentTemp;
    public int maxTemp;
    public int minTemp;
    public String weather;
    public String formattedDate;

    public DailyWeatherReport(String city, String country, int currentTemp, int maxTemp, int minTemp, String weather, String rawDate) {
        this.city = city;
        this.country = country;
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.weather = weather;
        this.formattedDate = rawDate;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public String getWeather() {
        return weather;
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}

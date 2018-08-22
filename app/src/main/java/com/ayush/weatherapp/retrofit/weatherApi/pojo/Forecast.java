package com.ayush.weatherapp.retrofit.weatherApi.pojo;

import com.google.gson.annotations.SerializedName;

public class Forecast {
  @SerializedName("latitude") private double latitude;
  @SerializedName("longitude") private double longitude;
  @SerializedName("timezone") private String timezone;

  @SerializedName("currently") private CurrentForecast currentForecast;
  @SerializedName("hourly") private HourlyForecast hourlyForecast;
  @SerializedName("daily") private DailyForecast dailyForecast;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public CurrentForecast getCurrentForecast() {
    return currentForecast;
  }

  public HourlyForecast getHourlyForecast() {
    return hourlyForecast;
  }

  public DailyForecast getDailyForecast() {
    return dailyForecast;
  }
}

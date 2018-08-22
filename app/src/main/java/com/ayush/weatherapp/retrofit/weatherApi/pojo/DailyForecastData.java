package com.ayush.weatherapp.retrofit.weatherApi.pojo;

import com.google.gson.annotations.SerializedName;

public class DailyForecastData {
  @SerializedName("time") private long time;
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("sunriseTime") private int sunriseTime;
  @SerializedName("sunsetTime") private int sunsetTime;
  @SerializedName("temperatureHigh") private double temperatureHigh;
  @SerializedName("temperatureLow") private double temperatureLow;
  @SerializedName("apparentTemperatureHigh") private double apparentTemperatureHigh;
  @SerializedName("apparentTemperatureLow") private double apparentTemperatureLow;
  @SerializedName("windSpeed") private double windSpeed;

  public long getTime() {
    return time;
  }

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public int getSunriseTime() {
    return sunriseTime;
  }

  public int getSunsetTime() {
    return sunsetTime;
  }

  public double getTemperatureHigh() {
    return temperatureHigh;
  }

  public double getTemperatureLow() {
    return temperatureLow;
  }

  public double getApparentTemperatureHigh() {
    return apparentTemperatureHigh;
  }

  public double getApparentTemperatureLow() {
    return apparentTemperatureLow;
  }

  public double getWindSpeed() {
    return windSpeed;
  }
}

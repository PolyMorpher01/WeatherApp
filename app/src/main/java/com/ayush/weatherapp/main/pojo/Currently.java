package com.ayush.weatherapp.main.pojo;

import com.google.gson.annotations.SerializedName;

public class Currently {
  @SerializedName("time") private int time;
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("temperature") private double temperature;
  @SerializedName("apparentTemperature") private double apparentTemperature;
  @SerializedName("dewPoint") private double dewPoint;
  @SerializedName("humidity") private double humidity;
  @SerializedName("pressure") private double pressure;
  @SerializedName("windSpeed") private double windSpeed;
  @SerializedName("visibility") private double visibility;

  public int getTime() {
    return time;
  }

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public double getTemperature() {
    return temperature;
  }

  public double getApparentTemperature() {
    return apparentTemperature;
  }

  public double getDewPoint() {
    return dewPoint;
  }

  public double getHumidity() {
    return humidity;
  }

  public double getPressure() {
    return pressure;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public double getVisibility() {
    return visibility;
  }
}

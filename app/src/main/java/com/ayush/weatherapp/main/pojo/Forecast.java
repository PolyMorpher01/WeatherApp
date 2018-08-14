package com.ayush.weatherapp.main.pojo;

import com.google.gson.annotations.SerializedName;

public class Forecast {
  @SerializedName("latitude") private double latitude;
  @SerializedName("longitude") private double longitude;
  @SerializedName("timezone") private String timezone;

  @SerializedName("currently") private Currently currently = null;
  @SerializedName("hourly") private Hourly hourly = null;
  @SerializedName("daily") private Daily daily = null;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public Currently getCurrently() {
    return currently;
  }

  public Hourly getHourly() {
    return hourly;
  }

  public Daily getDaily() {
    return daily;
  }
}

package com.ayush.weatherapp.retrofit.weatherApi.pojo;

import com.google.gson.annotations.SerializedName;

public class ForecastDTO {
  @SerializedName("latitude") private double latitude;
  @SerializedName("longitude") private double longitude;
  @SerializedName("timezone") private String timezone;

  @SerializedName("currently") private CurrentForecastDTO currentForecastDTO;
  @SerializedName("hourly") private HourlyForecastDTO hourlyForecastDTO;
  @SerializedName("daily") private DailyForecastDTO dailyForecastDTO;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public CurrentForecastDTO getCurrentForecastDTO() {
    return currentForecastDTO;
  }

  public HourlyForecastDTO getHourlyForecastDTO() {
    return hourlyForecastDTO;
  }

  public DailyForecastDTO getDailyForecastDTO() {
    return dailyForecastDTO;
  }
}

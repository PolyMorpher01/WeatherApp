package com.ayush.weatherapp.home.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyForecast {
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("data") private List<HourlyData> hourlyDataList;

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public List<HourlyData> getHourlyDataList() {
    return hourlyDataList;
  }

  public static class HourlyData {
    @SerializedName("time") private int time;
    @SerializedName("summary") private String summary;
    @SerializedName("icon") private String icon;
    @SerializedName("temperature") private float temperature;
    @SerializedName("apparentTemperature") private float apparentTemperature;

    public int getTime() {
      return time;
    }

    public String getSummary() {
      return summary;
    }

    public String getIcon() {
      return icon;
    }

    public float getTemperature() {
      return temperature;
    }

    public float getApparentTemperature() {
      return apparentTemperature;
    }

  }
}



package com.ayush.weatherapp.retrofit.weatherApi.pojo;

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
}

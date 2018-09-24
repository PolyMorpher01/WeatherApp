package com.ayush.weatherapp.retrofit.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyForecastDTO {
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("data") private List<HourlyDataDTO> hourlyDataDTOList;

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public List<HourlyDataDTO> getHourlyDataDTOList() {
    return hourlyDataDTOList;
  }
}

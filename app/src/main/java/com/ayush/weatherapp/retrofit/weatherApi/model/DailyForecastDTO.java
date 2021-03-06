package com.ayush.weatherapp.retrofit.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyForecastDTO {
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("data") private List<DailyDataDTO> dailyDataDTOList;

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public List<DailyDataDTO> getDailyDataDTOList() {
    return dailyDataDTOList;
  }
}

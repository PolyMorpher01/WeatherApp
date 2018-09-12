package com.ayush.weatherapp.entities.forecast;

import java.util.List;

public class DailyForecastEntity {
  private String summary;
  private String icon;
  private List<DailyDataEntity> dailyDataEntityList;
  private static int TODAY_INDEX = 0;

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public List<DailyDataEntity> getDailyDataEntityList() {
    return dailyDataEntityList;
  }

  public void setDailyDataEntityList(List<DailyDataEntity> dailyDataEntityList) {
    this.dailyDataEntityList = dailyDataEntityList;
  }

  public DailyDataEntity getTodaysDataEntity() {
    return dailyDataEntityList.get(TODAY_INDEX);
  }
}

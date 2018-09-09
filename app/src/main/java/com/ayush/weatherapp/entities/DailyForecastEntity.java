package com.ayush.weatherapp.entities;

import java.util.List;

public class DailyForecastEntity {
  private String summary;
  private String icon;
  private List<DailyDataEntity> dailyDataEntityList;

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
}

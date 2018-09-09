package com.ayush.weatherapp.entities;

import java.util.List;

public class HourlyForecastEntity {
  private String summary;
  private String icon;
  private List<HourlyDataEntity> hourlyDataEntityList;

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

  public List<HourlyDataEntity> getHourlyDataEntityList() {
    return hourlyDataEntityList;
  }

  public void setHourlyDataEntityList(List<HourlyDataEntity> hourlyDataEntityList) {
    this.hourlyDataEntityList = hourlyDataEntityList;
  }
}

package com.ayush.weatherapp.entities.forecast;

import io.realm.annotations.PrimaryKey;
import java.util.List;

public class HourlyForecastEntity {
  @PrimaryKey private long primaryKey;
  private String summary;
  private String icon;
  private List<HourlyDataEntity> hourlyDataEntityList;

  public long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

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

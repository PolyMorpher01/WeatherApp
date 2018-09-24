package com.ayush.weatherapp.entities.forecast;

import io.realm.annotations.PrimaryKey;
import java.util.List;

public class DailyForecastEntity {
  private static int TODAY_INDEX = 0;
  @PrimaryKey private long primaryKey;
  private String summary;
  private String icon;
  private List<DailyDataEntity> dailyDataEntityList;

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

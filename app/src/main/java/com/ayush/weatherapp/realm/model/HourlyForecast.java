package com.ayush.weatherapp.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

public class HourlyForecast extends RealmObject {

  @PrimaryKey private long primaryKey;
  private String summary;
  private String icon;
  private RealmList<HourlyData> hourlyDataList;

  public HourlyForecast() {
  }

  public HourlyForecast(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getPrimaryKey() {
    return primaryKey;
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

  public List<HourlyData> getHourlyDataList() {
    return hourlyDataList;
  }

  public void setHourlyDataList(List<HourlyData> hourlyDataList) {
    this.hourlyDataList = new RealmList<>();
    this.hourlyDataList.addAll(hourlyDataList);
  }
}

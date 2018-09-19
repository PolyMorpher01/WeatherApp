package com.ayush.weatherapp.realm.model.forecast;

import com.ayush.weatherapp.realm.RealmDeletable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

public class HourlyForecast extends RealmObject implements RealmDeletable {
  private static final int MAX_NUMBER_OF_DATA = 6;

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
    return new ArrayList<>(hourlyDataList.subList(0, MAX_NUMBER_OF_DATA));
  }

  public void setHourlyDataList(List<HourlyData> hourlyDataList) {
    this.hourlyDataList = new RealmList<>();
    this.hourlyDataList.addAll(hourlyDataList);
  }

  @Override public void removeFromRealm() {
    if (hourlyDataList != null) {
      for (HourlyData hourlyData : hourlyDataList) {
        hourlyData.removeFromRealm();
      }
    }
    deleteFromRealm();
  }
}

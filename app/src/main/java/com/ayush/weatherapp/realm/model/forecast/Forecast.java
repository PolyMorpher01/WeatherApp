package com.ayush.weatherapp.realm.model.forecast;

import com.ayush.weatherapp.realm.RealmDeletable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Forecast extends RealmObject implements RealmDeletable {

  @PrimaryKey private long primaryKey;
  private CurrentForecast currentForecast;
  private HourlyForecast hourlyForecast;
  private DailyForecast dailyForecast;
  private long createdAt;

  public Forecast() {

  }

  public Forecast(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public CurrentForecast getCurrentForecast() {
    return currentForecast;
  }

  public void setCurrentForecast(CurrentForecast currentForecast) {
    this.currentForecast = currentForecast;
  }

  public HourlyForecast getHourlyForecast() {
    return hourlyForecast;
  }

  public void setHourlyForecast(HourlyForecast hourlyForecast) {
    this.hourlyForecast = hourlyForecast;
  }

  public DailyForecast getDailyForecast() {
    return dailyForecast;
  }

  public void setDailyForecast(DailyForecast dailyForecast) {
    this.dailyForecast = dailyForecast;
  }

  @Override public void removeFromRealm() {
    if (currentForecast != null) {
      currentForecast.removeFromRealm();
    }
    if (hourlyForecast != null) {
      hourlyForecast.removeFromRealm();
    }
    if (dailyForecast != null) {
      dailyForecast.removeFromRealm();
    }
    deleteFromRealm();
  }
}

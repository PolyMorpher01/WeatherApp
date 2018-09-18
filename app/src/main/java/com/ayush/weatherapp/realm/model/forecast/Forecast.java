package com.ayush.weatherapp.realm.model.forecast;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Forecast extends RealmObject {

  @PrimaryKey private long primaryKey;
  private CurrentForecast currentForecast;
  private HourlyForecast hourlyForecast;
  private DailyForecast dailyForecast;

  public Forecast() {

  }

  public Forecast(long primaryKey) {
    this.primaryKey = primaryKey;
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
}

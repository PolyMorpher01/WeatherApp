package com.ayush.weatherapp.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Forecast extends RealmObject {

  @PrimaryKey private long primaryKey;
  private double latitude;
  private double longitude;
  private String timezone;
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

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
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

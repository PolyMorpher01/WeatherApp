package com.ayush.weatherapp.realm.model.forecast;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DailyData extends RealmObject {

  @PrimaryKey private long primaryKey;
  private long time;
  private String summary;
  private String icon;
  private int sunriseTime;
  private int sunsetTime;
  private double temperatureHigh;
  private double temperatureLow;
  private double apparentTemperatureHigh;
  private double apparentTemperatureLow;
  private double windSpeed;

  public DailyData() {
  }

  public DailyData(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
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

  public int getSunriseTime() {
    return sunriseTime;
  }

  public void setSunriseTime(int sunriseTime) {
    this.sunriseTime = sunriseTime;
  }

  public int getSunsetTime() {
    return sunsetTime;
  }

  public void setSunsetTime(int sunsetTime) {
    this.sunsetTime = sunsetTime;
  }

  public double getTemperatureHigh() {
    return temperatureHigh;
  }

  public void setTemperatureHigh(double temperatureHigh) {
    this.temperatureHigh = temperatureHigh;
  }

  public double getTemperatureLow() {
    return temperatureLow;
  }

  public void setTemperatureLow(double temperatureLow) {
    this.temperatureLow = temperatureLow;
  }

  public double getApparentTemperatureHigh() {
    return apparentTemperatureHigh;
  }

  public void setApparentTemperatureHigh(double apparentTemperatureHigh) {
    this.apparentTemperatureHigh = apparentTemperatureHigh;
  }

  public double getApparentTemperatureLow() {
    return apparentTemperatureLow;
  }

  public void setApparentTemperatureLow(double apparentTemperatureLow) {
    this.apparentTemperatureLow = apparentTemperatureLow;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }
}

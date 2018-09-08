package com.ayush.weatherapp.entities;

public class DailyDataEntity {
  private long time;
  private String summary;
  private String icon;
  private int sunriseTime;
  private int sunsetTime;
  private double temperatureHigh;
  private double temperatureLow;
  private double windSpeed;

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

  public double getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }
}

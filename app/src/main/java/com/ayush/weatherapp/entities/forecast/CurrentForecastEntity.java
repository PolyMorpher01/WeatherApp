package com.ayush.weatherapp.entities.forecast;

public class CurrentForecastEntity {
  private long time;
  private String summary;
  private String icon;
  private int temperature;

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

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = (int) temperature;
  }
}

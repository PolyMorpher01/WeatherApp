package com.ayush.weatherapp.realm.model.forecast;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentForecast extends RealmObject {

  @PrimaryKey private long primaryKey;
  private long time;
  private String summary;
  private String icon;
  private double temperature;
  private double apparentTemperature;
  private double dewPoint;
  private double humidity;
  private double pressure;
  private double windSpeed;
  private double visibility;

  public CurrentForecast() {
  }

  public CurrentForecast(long primaryKey) {
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

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getApparentTemperature() {
    return apparentTemperature;
  }

  public void setApparentTemperature(double apparentTemperature) {
    this.apparentTemperature = apparentTemperature;
  }

  public double getDewPoint() {
    return dewPoint;
  }

  public void setDewPoint(double dewPoint) {
    this.dewPoint = dewPoint;
  }

  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  public double getPressure() {
    return pressure;
  }

  public void setPressure(double pressure) {
    this.pressure = pressure;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  public double getVisibility() {
    return visibility;
  }

  public void setVisibility(double visibility) {
    this.visibility = visibility;
  }
}

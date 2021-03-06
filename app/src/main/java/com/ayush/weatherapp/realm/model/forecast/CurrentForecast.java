package com.ayush.weatherapp.realm.model.forecast;

import com.ayush.weatherapp.realm.RealmDeletable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentForecast extends RealmObject implements RealmDeletable {

  @PrimaryKey private long primaryKey;
  private long time;
  private String summary;
  private String icon;
  private double temperature;

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

  @Override public void removeFromRealm() {
    deleteFromRealm();
  }
}

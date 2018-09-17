package com.ayush.weatherapp.entities.geocoding;

import io.realm.annotations.PrimaryKey;

public class GeolocationEntity {
  @PrimaryKey private long primaryKey;
  private String location;

  public long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

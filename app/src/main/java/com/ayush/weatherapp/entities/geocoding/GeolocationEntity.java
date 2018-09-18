package com.ayush.weatherapp.entities.geocoding;

import io.realm.annotations.PrimaryKey;
import java.sql.Timestamp;

public class GeolocationEntity {
  @PrimaryKey private long primaryKey;
  private String location;
  private long createdAt;

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

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

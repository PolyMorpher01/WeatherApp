package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.sql.Timestamp;

public class GeoLocation extends RealmObject {
  @PrimaryKey private long primaryKey;
  private String location;
  private long createdAt;

  public GeoLocation() {
  }

  public GeoLocation(long primaryKey) {
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

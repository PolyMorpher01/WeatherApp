package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GeoLocation extends RealmObject {
  @PrimaryKey private long primaryKey;
  private String location;

  public GeoLocation() {
  }

  public GeoLocation(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

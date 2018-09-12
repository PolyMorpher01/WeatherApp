package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LocationCoordinates extends RealmObject {
  @PrimaryKey private long primaryKey;
  private double latitude;
  private double longitude;

  public LocationCoordinates() {
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
}

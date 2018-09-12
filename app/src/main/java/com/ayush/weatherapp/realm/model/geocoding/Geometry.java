package com.ayush.weatherapp.realm.model.geocoding;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.LocationCoordinatesDTO;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Geometry extends RealmObject {

  @PrimaryKey private long primaryKey;
  private LocationCoordinates locationCoordinates;
  private String locationType;

  public Geometry() {
  }

  public Geometry(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public LocationCoordinates getLocationCoordinates() {
    return locationCoordinates;
  }

  public void setLocationCoordinates(
      LocationCoordinates locationCoordinates) {
    this.locationCoordinates = locationCoordinates;
  }

  public String getLocationType() {
    return locationType;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }
}

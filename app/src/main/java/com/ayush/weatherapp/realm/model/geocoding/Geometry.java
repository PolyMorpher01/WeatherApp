package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;

public class Geometry extends RealmObject {
  private LocationCoordinates locationCoordinates;
  private String locationType;

  public Geometry() {
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

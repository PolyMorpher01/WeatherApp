package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;

public class LocationTypes extends RealmObject {
  private String types;

  public LocationTypes() {
  }

  public String getTypes() {
    return types;
  }

  public void setTypes(String types) {
    this.types = types;
  }
}

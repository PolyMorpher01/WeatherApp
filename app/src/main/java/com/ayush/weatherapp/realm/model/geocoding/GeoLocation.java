package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GeoLocation extends RealmObject {
  @PrimaryKey private long primaryKey;
  private Address address;
  private String status;

  public GeoLocation() {
  }

  public GeoLocation(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

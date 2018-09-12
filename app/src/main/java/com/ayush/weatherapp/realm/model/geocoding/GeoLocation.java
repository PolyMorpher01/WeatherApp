package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

public class GeoLocation extends RealmObject {
  @PrimaryKey private long primaryKey;
  private RealmList<Address> address;
  private String status;

  public GeoLocation() {
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = new RealmList<>();
    this.address.addAll(address);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

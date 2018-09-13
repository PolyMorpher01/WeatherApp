package com.ayush.weatherapp.entities.geocoding;

import java.util.List;

public class GeolocationEntity {
  private List<AddressEntity> address;
  private String status;

  public List<AddressEntity> getAddress() {
    return address;
  }

  public void setAddress(List<AddressEntity> address) {
    this.address = address;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

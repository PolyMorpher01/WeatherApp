package com.ayush.weatherapp.entities.geocoding;

import java.util.List;

public class AddressEntity {
  private List<AddressComponentsEntity> addressComponents;
  private String formattedAddress;

  public List<AddressComponentsEntity> getAddressComponents() {
    return addressComponents;
  }

  public void setAddressComponents(
      List<AddressComponentsEntity> addressComponents) {
    this.addressComponents = addressComponents;
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public void setFormattedAddress(String formattedAddress) {
    this.formattedAddress = formattedAddress;
  }
}

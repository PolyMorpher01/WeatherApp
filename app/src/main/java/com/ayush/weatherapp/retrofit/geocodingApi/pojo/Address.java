package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Address {
  @SerializedName("address_components") private List<AddressComponents> addressComponents;
  @SerializedName("formatted_address") private String formattedAddress;
  @SerializedName("geometry") private Geometry geometry;

  public List<AddressComponents> getAddressComponents() {
    return addressComponents;
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public Geometry getGeometry() {
    return geometry;
  }
}


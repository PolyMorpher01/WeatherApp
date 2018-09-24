package com.ayush.weatherapp.retrofit.geocodingApi.model;

import com.google.gson.annotations.SerializedName;

public class GeoLocationDTO {
  @SerializedName("display_name") private String displayName;
  @SerializedName("address") private AddressDTO addressDTO;

  public String getDisplayName() {
    return displayName;
  }

  public AddressDTO getAddressDTO() {
    return addressDTO;
  }
}

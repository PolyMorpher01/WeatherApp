package com.ayush.weatherapp.retrofit.geocodingApi.model;

import com.google.gson.annotations.SerializedName;

public class AddressDTO {
  @SerializedName("road") private String street;
  @SerializedName("city") private String city;
  @SerializedName("state_district") private String stateDistrict;
  @SerializedName("state") private String state;
  @SerializedName("country") private String country;

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getStateDistrict() {
    return stateDistrict;
  }

  public String getState() {
    return state;
  }

  public String getCountry() {
    return country;
  }
}

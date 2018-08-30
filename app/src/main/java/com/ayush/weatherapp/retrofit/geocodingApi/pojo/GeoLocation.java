package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GeoLocation {
  @SerializedName("results") private List<Address> addresses;
  @SerializedName("status") private String status;

  public List<Address> getAddresses() {
    return addresses;
  }

  public String getStatus() {
    return status;
  }
}

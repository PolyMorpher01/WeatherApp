package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GeoLocationDTO {
  @SerializedName("results") private List<AddressDTO> addressDTOS;
  @SerializedName("status") private String status;

  public List<AddressDTO> getAddressDTOS() {
    return addressDTOS;
  }

  public String getStatus() {
    return status;
  }
}

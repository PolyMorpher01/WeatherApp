package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AddressDTO {
  @SerializedName("address_components") private List<AddressComponentsDTO> addressComponentDTOS;
  @SerializedName("formatted_address") private String formattedAddress;
  @SerializedName("geometryDTO") private GeometryDTO geometryDTO;

  public List<AddressComponentsDTO> getAddressComponentDTOS() {
    return addressComponentDTOS;
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public GeometryDTO getGeometryDTO() {
    return geometryDTO;
  }
}


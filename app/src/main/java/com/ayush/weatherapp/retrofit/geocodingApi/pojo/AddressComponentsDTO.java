package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AddressComponentsDTO {
  @SerializedName("long_name") private String longName;
  @SerializedName("short_name") private String shortName;
  @SerializedName("types") private List<String> types;

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  public List<String> getTypes() {
    return types;
  }
}

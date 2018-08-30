package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;

public class Geometry {
  @SerializedName("location") private LocationCoordinates locationCoordinates;
  @SerializedName("location_type") private String locationType;

  public LocationCoordinates getLocationCoordinates() {
    return locationCoordinates;
  }

  public String getLocationType() {
    return locationType;
  }
}

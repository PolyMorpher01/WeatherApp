package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;

public class GeometryDTO {
  @SerializedName("location") private LocationCoordinatesDTO locationCoordinatesDTO;
  @SerializedName("location_type") private String locationType;

  public LocationCoordinatesDTO getLocationCoordinatesDTO() {
    return locationCoordinatesDTO;
  }

  public String getLocationType() {
    return locationType;
  }
}

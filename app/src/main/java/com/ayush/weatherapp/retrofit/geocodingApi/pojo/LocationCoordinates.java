package com.ayush.weatherapp.retrofit.geocodingApi.pojo;

import com.google.gson.annotations.SerializedName;

public class LocationCoordinates {
  @SerializedName("lat") private double latitude;
  @SerializedName("lng") private double longitude;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}

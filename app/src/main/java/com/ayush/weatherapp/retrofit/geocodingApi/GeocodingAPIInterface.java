package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("json?")
  Call<GeoLocation> getLocationDetails(
      @Query("latlng") String latlng);

  @GET("json?")
  Call<GeoLocation> getLatLng(
      @Query("address") String address);
}

package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.ReverseGeoLocation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("json?")
  Call<ReverseGeoLocation> getLocationDetails(
      @Query("latlng") String latlng,
      @Query("key") String key);
}

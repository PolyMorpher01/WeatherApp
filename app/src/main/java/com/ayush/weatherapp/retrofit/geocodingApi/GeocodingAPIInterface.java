package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.model.GeoLocationDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("reverse?format=jsonv2")
  Observable<GeoLocationDTO> getLocationDetails(
      @Query("lat") double lat,
      @Query("lon") double lon);
}

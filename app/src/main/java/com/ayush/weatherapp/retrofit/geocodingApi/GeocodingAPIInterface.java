package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("json?") Single<GeoLocationDTO> getLocationDetails(@Query("latlng") String latlng);
}

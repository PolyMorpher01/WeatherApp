package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("json?") Observable<GeoLocationDTO> getLocationDetails(@Query("latlng") String latlng);
}

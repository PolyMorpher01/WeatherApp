package com.ayush.weatherapp.retrofit.geocodingApi;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocation;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPIInterface {
  @GET("json?") Observable<GeoLocation> getLocationDetailsRX(@Query("latlng") String latlng);
}

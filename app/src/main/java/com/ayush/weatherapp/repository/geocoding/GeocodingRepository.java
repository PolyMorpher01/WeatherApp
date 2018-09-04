package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocation;
import io.reactivex.Observable;

public interface GeocodingRepository {

  Observable<GeoLocation> getLocationDetails(String latlng);
}

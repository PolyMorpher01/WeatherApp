package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import io.reactivex.Observable;

public interface GeocodingRepository {

  Observable<GeolocationEntity> getLocation(String latlng);
}

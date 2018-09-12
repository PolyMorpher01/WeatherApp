package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import io.reactivex.Observable;

public interface GeocodingRepository {

  Observable<GeoLocationDTO> getLocationDetails(String latlng);
}

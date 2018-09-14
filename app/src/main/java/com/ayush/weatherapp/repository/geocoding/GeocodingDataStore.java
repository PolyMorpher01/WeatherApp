package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import io.reactivex.Single;

public interface GeocodingDataStore {
  Single<GeoLocation> getLocation(String latlng);
}

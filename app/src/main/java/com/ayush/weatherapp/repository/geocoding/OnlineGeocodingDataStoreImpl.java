package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.mapper.GeocodingDTOToRealmMapper;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import io.reactivex.Single;

public class OnlineGeocodingDataStoreImpl implements GeocodingDataStore {
  private GeocodingAPIInterface geocodingAPIInterface;

  public OnlineGeocodingDataStoreImpl() {
    geocodingAPIInterface = GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);
  }

  @Override public Single<GeoLocation> getLocation(String latlng) {
    return geocodingAPIInterface.getLocationDetails(latlng)
        .map(GeocodingDTOToRealmMapper::transform);
  }
}

package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingDTOToRealmMapper;
import com.ayush.weatherapp.mapper.GeocodingRealmToEntityMapper;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import io.reactivex.Observable;

public class RemoteGeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingAPIInterface geocodingAPIInterface;

  public RemoteGeocodingRepositoryImpl() {
    geocodingAPIInterface = GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);
  }

  @Override
  public Observable<GeolocationEntity> getLocation(double lat, double lng,
      boolean isCurrentLocation) {
    return geocodingAPIInterface.getLocationDetails(lat, lng)
        .map(GeocodingDTOToRealmMapper::transform)
        .map(GeocodingRealmToEntityMapper::transform);
  }
}

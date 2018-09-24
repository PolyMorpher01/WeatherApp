package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingDTOToRealmMapper;
import com.ayush.weatherapp.mapper.GeocodingRealmToEntityMapper;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import io.reactivex.Observable;
import javax.inject.Inject;

public class RemoteGeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingAPIInterface geocodingAPIInterface;

 @Inject public RemoteGeocodingRepositoryImpl(GeocodingAPIInterface geocodingAPIInterface) {
    this.geocodingAPIInterface = geocodingAPIInterface;
  }

  @Override
  public Observable<GeolocationEntity> getLocation(double lat, double lng,
      boolean isCurrentLocation) {
    return geocodingAPIInterface.getLocationDetails(lat, lng)
        .map(GeocodingDTOToRealmMapper::transform)
        .map(GeocodingRealmToEntityMapper::transform);
  }
}

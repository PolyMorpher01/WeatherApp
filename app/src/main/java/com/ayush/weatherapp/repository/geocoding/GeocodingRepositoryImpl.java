package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import io.reactivex.Observable;

public class GeocodingRepositoryImpl implements GeocodingRepository {

  private GeocodingAPIInterface geocodingAPIInterface;

  // TODO provide dependencies using dagger
  public GeocodingRepositoryImpl() {
    geocodingAPIInterface = GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);
  }

  @Override public Observable<GeoLocationDTO> getLocationDetails(String latlng) {
    return geocodingAPIInterface.getLocationDetails(latlng);
  }
}

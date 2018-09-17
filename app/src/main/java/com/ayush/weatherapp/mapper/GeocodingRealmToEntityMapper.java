package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;

public final class GeocodingRealmToEntityMapper {
  private GeocodingRealmToEntityMapper() {
  }

  public static GeolocationEntity transform(GeoLocation geolocation) {
    GeolocationEntity entity = new GeolocationEntity();
    entity.setLocation(geolocation.getLocation());
    return entity;
  }
}

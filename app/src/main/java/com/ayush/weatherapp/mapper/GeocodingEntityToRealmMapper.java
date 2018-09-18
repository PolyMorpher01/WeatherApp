package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;

public final class GeocodingEntityToRealmMapper {
  private GeocodingEntityToRealmMapper() {
  }

  public static GeoLocation transform(GeolocationEntity entity){
    GeoLocation geoLocation = new GeoLocation(entity.getPrimaryKey());
    geoLocation.setLocation(entity.getLocation());
    geoLocation.setCreatedAt(entity.getCreatedAt());
    return geoLocation;
  }

}

package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import io.reactivex.Single;

public class LocalGeocodingDataStoreImpl implements GeocodingDataStore {
  @Override public Single<GeoLocation> getLocation(String latlng) {
    GeoLocation geoLocation = RealmUtils.getRealmModel(GeoLocation.class,
        RealmUtils.getMaxIdForPrimaryKey(GeoLocation.class));

    if (geoLocation != null) {
      return Single.just(geoLocation);
    }
    return Single.error(new NullPointerException());
  }
}

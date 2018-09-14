package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingRealmToEntityMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import io.reactivex.Observable;
import io.realm.Realm;

public class GeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingDataStore onlineDataStore;
  private GeocodingDataStore localDataStore;

  // TODO provide dependencies using dagger
  public GeocodingRepositoryImpl() {
    onlineDataStore = new OnlineGeocodingDataStoreImpl();
    localDataStore = new LocalGeocodingDataStoreImpl();
  }

  @Override public Observable<GeolocationEntity> getLocation(String latlng) {
    return Observable.create(emitter -> {
      localDataStore.getLocation(latlng)
          .map(GeocodingRealmToEntityMapper::transform)
          .subscribe(emitter::onNext, throwable -> {
          });

      onlineDataStore.getLocation(latlng)
          .map(geolocation -> {
            saveGeoLocationDetails(geolocation);
            return GeocodingRealmToEntityMapper.transform(geolocation);
          })
          .subscribe(value -> {
            emitter.onNext(value);
            emitter.onComplete();
          }, emitter::onError);
    });
  }

  private void saveGeoLocationDetails(GeoLocation geoLocation) {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(geoLocation));
    realm.close();
  }
}

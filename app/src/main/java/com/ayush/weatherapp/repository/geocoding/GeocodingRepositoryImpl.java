package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import io.reactivex.Observable;
import io.realm.Realm;

public class GeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingRepository onlineDataStore;
  private GeocodingRepository localDataStore;

  // TODO provide dependencies using dagger
  public GeocodingRepositoryImpl() {
    onlineDataStore = new OnlineGeocodingRepositoryImpl();
    localDataStore = new LocalGeocodingRepositoryImpl();
  }

  @Override public Observable<GeolocationEntity> getLocation(String latlng) {
    return Observable.create(emitter -> {
      localDataStore.getLocation(latlng)
          .subscribe(emitter::onNext, throwable -> {
          });

      onlineDataStore.getLocation(latlng)
          .map(geolocation -> {
            saveGeoLocationDetails(GeocodingEntityToRealmMapper.transform(geolocation));
            return geolocation;
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

  private boolean isSavedLocally() {
    Realm realm = RealmUtils.getRealm();
    boolean isSaved = realm.where(GeoLocation.class).count() > 0;
    return isSaved;
  }
}

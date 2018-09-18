package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import io.reactivex.Observable;
import io.realm.Realm;
import timber.log.Timber;

public class GeocodingRepositoryImpl implements GeocodingRepository {
  private static final long FIVE_MINUTES = 5 * 60 * 1000;
  private GeocodingRepository onlineDataStore;
  private GeocodingRepository localDataStore;

  // TODO provide dependencies using dagger
  public GeocodingRepositoryImpl() {
    onlineDataStore = new OnlineGeocodingRepositoryImpl();
    localDataStore = new LocalGeocodingRepositoryImpl();
  }

  @Override
  public Observable<GeolocationEntity> getLocation(String latlng, boolean isCurrentLocation) {
    if (isSavedLocally() && isCurrentLocation) {
      return getlocalObservable(latlng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last call was more than five minutes ogo
            if (isFiveMinutesPassed(entity)) {
              return getOnlineObservable(latlng, isCurrentLocation);
            }
            return getlocalObservable(latlng, isCurrentLocation);
          });
    }
    return getOnlineObservable(latlng, isCurrentLocation);
  }

  private boolean isSavedLocally() {
    Realm realm = RealmUtils.getRealm();
    boolean isSavedLocally = realm.where(GeoLocation.class).count() > 0;
    realm.close();
    return isSavedLocally;
  }

  private Observable<GeolocationEntity> getlocalObservable(String latlng,
      boolean isCurrentLocation) {
    return localDataStore.getLocation(latlng, isCurrentLocation);
  }

  private Observable<GeolocationEntity> getOnlineObservable(String latlng,
      boolean isCurrentLocation) {
    return onlineDataStore.getLocation(latlng, isCurrentLocation)
        .map(geolocation -> {
          //save details of current location only
          if (isCurrentLocation) {
            saveGeoLocationDetails(GeocodingEntityToRealmMapper.transform(geolocation));
          }
          return geolocation;
        });
  }

  private boolean isFiveMinutesPassed(GeolocationEntity entity) {
    long fiveMinutesAgo = System.currentTimeMillis() - FIVE_MINUTES;
    if (entity.getCreatedAt() < fiveMinutesAgo) {
      Timber.e("five minutes Ago!!");
      return true;
    }
    return false;
  }

  private void saveGeoLocationDetails(GeoLocation geoLocation) {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(geoLocation));
    realm.close();
  }
}

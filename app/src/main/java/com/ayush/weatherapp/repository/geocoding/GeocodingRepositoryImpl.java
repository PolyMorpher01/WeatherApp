package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mapper.GeocodingEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.utils.DateUtils;
import io.reactivex.Observable;
import io.realm.Realm;

public class GeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingRepository onlineRepository;
  private GeocodingRepository localRepository;

  // TODO provide dependencies using dagger
  public GeocodingRepositoryImpl() {
    onlineRepository = new OnlineGeocodingRepositoryImpl();
    localRepository = new LocalGeocodingRepositoryImpl();
  }

  @Override
  public Observable<GeolocationEntity> getLocation(String latlng, boolean isCurrentLocation) {
    if (isSavedLocally() && isCurrentLocation) {
      return getlocalObservable(latlng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last row created was more than five minutes ago
            if (DateUtils.isFiveMinutesAgo(entity.getCreatedAt())) {
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
    return localRepository.getLocation(latlng, isCurrentLocation);
  }

  private Observable<GeolocationEntity> getOnlineObservable(String latlng,
      boolean isCurrentLocation) {
    return onlineRepository.getLocation(latlng, isCurrentLocation)
        .map(geolocation -> {
          //save details of current location only
          if (isCurrentLocation) {
            saveGeoLocationDetails(GeocodingEntityToRealmMapper.transform(geolocation));
          }
          return geolocation;
        });
  }

  private void saveGeoLocationDetails(GeoLocation geoLocation) {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(geoLocation));
    realm.close();
  }
}

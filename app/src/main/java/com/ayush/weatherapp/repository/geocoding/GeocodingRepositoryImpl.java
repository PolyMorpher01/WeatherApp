package com.ayush.weatherapp.repository.geocoding;

import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.injection.annotations.Local;
import com.ayush.weatherapp.injection.annotations.Remote;
import com.ayush.weatherapp.mapper.GeocodingEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.utils.DateUtils;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.List;
import javax.inject.Inject;

public class GeocodingRepositoryImpl implements GeocodingRepository {
  private GeocodingRepository remoteRepository;
  private GeocodingRepository localRepository;

  @Inject
  public GeocodingRepositoryImpl(@Local LocalGeocodingRepositoryImpl localGeocodingRepository,
      @Remote RemoteGeocodingRepositoryImpl remoteGeocodingRepository) {
    localRepository = localGeocodingRepository;
    remoteRepository = remoteGeocodingRepository;
  }

  @Override
  public Observable<GeolocationEntity> getLocation(double lat, double lng,
      boolean isCurrentLocation) {
    if (RealmUtils.isSavedLocally(GeoLocation.class) && isCurrentLocation) {
      return getLocalGeolocation(lat, lng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last row created was more than five minutes ago
            if (DateUtils.isFiveMinutesAgo(entity.getCreatedAt())) {
              return getRemoteGeolocation(lat, lng, isCurrentLocation);
            }
            return getLocalGeolocation(lat, lng, isCurrentLocation);
          });
    }
    return getRemoteGeolocation(lat, lng, isCurrentLocation);
  }

  private Observable<GeolocationEntity> getLocalGeolocation(double lat, double lng,
      boolean isCurrentLocation) {
    return localRepository.getLocation(lat, lng, isCurrentLocation);
  }

  private Observable<GeolocationEntity> getRemoteGeolocation(double lat, double lng,
      boolean isCurrentLocation) {
    return remoteRepository.getLocation(lat, lng, isCurrentLocation)
        .doOnNext(geoLocation -> {

          //save details of current location only
          if (isCurrentLocation) {
            saveGeoLocationDetails(GeocodingEntityToRealmMapper.transform(geoLocation));
          }
        });
  }

  private void saveGeoLocationDetails(GeoLocation geoLocation) {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> {
      // delete all previously saved data and save new data to database
      List<GeoLocation> storedGeoLocations = r.where(GeoLocation.class).findAll();
      if (storedGeoLocations != null) {
        for (GeoLocation storedGeoLocation : storedGeoLocations) {
          storedGeoLocation.removeFromRealm();
        }
      }
      r.insert(geoLocation);
    });

    realm.close();
  }
}

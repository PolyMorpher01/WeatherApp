package com.ayush.weatherapp.repository.forecast;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.injection.annotations.Local;
import com.ayush.weatherapp.injection.annotations.Remote;
import com.ayush.weatherapp.mapper.ForecastEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.utils.DateUtils;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.List;
import javax.inject.Inject;

public class ForecastRepositoryImpl implements ForecastRepository {
  private ForecastRepository remoteForecastRepository;
  private ForecastRepository localForecastRepository;

  @Inject public ForecastRepositoryImpl(@Local LocalForecastRepositoryImpl localForecastRepository,
     @Remote RemoteForecastRepositoryImpl remoteForecastRepository) {
    this.localForecastRepository = localForecastRepository;
    this.remoteForecastRepository = remoteForecastRepository;
  }

  @Override
  public Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation) {
    if (RealmUtils.isSavedLocally(Forecast.class) && isCurrentLocation) {
      return getLocalForecast(lat, lng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last row created was more than five minutes ago
            if (DateUtils.isFiveMinutesAgo(entity.getCreatedAt())) {
              return getRemoteForecast(lat, lng, isCurrentLocation);
            }
            return getLocalForecast(lat, lng, isCurrentLocation);
          });
    }
    return getRemoteForecast(lat, lng, isCurrentLocation);
  }

  private Observable<ForecastEntity> getLocalForecast(double lat, double lng,
      boolean isCurrentLocation) {
    return localForecastRepository.getForecast(lat, lng, isCurrentLocation);
  }

  private Observable<ForecastEntity> getRemoteForecast(double lat, double lng,
      boolean isCurrentLocation) {
    return remoteForecastRepository.getForecast(lat, lng, isCurrentLocation)
        .doOnNext(entity -> {
          //save details of current location only
          if (isCurrentLocation) {
            saveWeatherForecast(ForecastEntityToRealmMapper.transform(entity));
          }
        });
  }

  private void saveWeatherForecast(Forecast forecast) {
    Realm realm = RealmUtils.getRealm();

    realm.executeTransaction(r -> {
          // delete all previously saved data and save new data to database
          List<Forecast> storedForecasts = r.where(Forecast.class).findAll();
          if (storedForecasts != null) {
            for (Forecast storedForecast : storedForecasts) {
              storedForecast.removeFromRealm();
            }
          }
          r.insert(forecast);
        }
    );

    realm.close();
  }
}

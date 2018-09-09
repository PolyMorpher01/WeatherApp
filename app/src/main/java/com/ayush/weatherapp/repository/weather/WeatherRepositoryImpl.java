package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.ForecastEntity;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WeatherRepositoryImpl implements WeatherRepository {
  private WeatherDataStore onlineWeatherRepository;
  private WeatherDataStore localWeatherRepository;

  public WeatherRepositoryImpl() {
    onlineWeatherRepository = new OnlineWeatherDataStoreImpl();
    localWeatherRepository = new LocalWeatherDataStoreImpl();
  }

  @Override public Observable<ForecastEntity> getForecast(String coordinates) {
    return Observable.create(emitter -> {
      localWeatherRepository.getForecast(coordinates)
          .map(ForecastRealmToEntityMapper::transform)
          .subscribe(emitter::onNext, throwable -> {});

      onlineWeatherRepository.getForecast(coordinates)
          .map(forecast -> {
            saveWeatherForecast(forecast);
            return ForecastRealmToEntityMapper.transform(forecast);
          })
          .subscribe(emitter::onNext, emitter::onError);
    });
  }

  //private boolean isSavedLocally() {
  //  Realm realm = RealmUtils.getRealm();
  //  boolean isSaved = realm.where(Forecast.class).count() > 0;
  //  realm.close();
  //  return isSaved;
  //}

  private void saveWeatherForecast(Forecast forecast) {
    RealmUtils.removeAll();

    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(forecast));
    realm.close();
  }
}

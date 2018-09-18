package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import io.reactivex.Observable;

public class LocalWeatherRepositoryImpl implements WeatherRepository {
  public LocalWeatherRepositoryImpl() {
  }

  @Override public Observable<ForecastEntity> getForecast(String latlng, boolean isCurrentLocation) {
    Forecast realmModel =
        RealmUtils.getRealmModel(Forecast.class, RealmUtils.getMaxIdForPrimaryKey(Forecast.class));
    if (realmModel != null) {
      return Observable.just(realmModel).map(ForecastRealmToEntityMapper::transform);
    }
    return Observable.error(new NullPointerException());
  }
}

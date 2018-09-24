package com.ayush.weatherapp.repository.forecast;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import io.reactivex.Observable;

public class LocalForecastRepositoryImpl implements ForecastRepository {
  public LocalForecastRepositoryImpl() {
  }

  @Override public Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation) {
    Forecast realmModel =
        RealmUtils.getRealmModel(Forecast.class, RealmUtils.getMaxIdForPrimaryKey(Forecast.class));
    if (realmModel != null) {
      return Observable.just(realmModel).map(ForecastRealmToEntityMapper::transform);
    }
    return Observable.error(new NullPointerException());
  }
}

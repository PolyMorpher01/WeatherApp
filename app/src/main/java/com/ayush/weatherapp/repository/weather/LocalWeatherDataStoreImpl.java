package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;

public class LocalWeatherDataStoreImpl implements WeatherDataStore {
  public LocalWeatherDataStoreImpl() {
  }

  @Override public Observable<Forecast> getForecast(String coordinates) {
    Forecast realmModel = RealmUtils.getRealmModel(Forecast.class);
    return Observable.just(realmModel);
  }
}

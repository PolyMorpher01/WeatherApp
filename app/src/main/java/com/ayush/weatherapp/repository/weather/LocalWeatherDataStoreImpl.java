package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;
import io.reactivex.Single;

public class LocalWeatherDataStoreImpl implements WeatherDataStore {
  public LocalWeatherDataStoreImpl() {
  }

  @Override public Single<Forecast> getForecast(String coordinates) {
    Forecast realmModel = RealmUtils.getRealmModel(Forecast.class);
    return Single.just(realmModel);
  }
}

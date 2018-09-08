package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;
import java.util.Objects;

public class LocalWeatherRepositoryImpl implements WeatherRepository {
  public LocalWeatherRepositoryImpl() {
  }

  @Override public Observable<Forecast> getForecast(String coordinates) {
    return Observable.just(Objects.requireNonNull(RealmUtils.getRealmModel(Forecast.class)));
  }
}

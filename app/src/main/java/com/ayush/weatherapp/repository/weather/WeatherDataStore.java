package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface WeatherDataStore {
  Single<Forecast> getForecast(String coordinates);
}

package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.model.forecast.Forecast;
import io.reactivex.Single;

public interface WeatherDataStore {
  Single<Forecast> getForecast(String coordinates);
}

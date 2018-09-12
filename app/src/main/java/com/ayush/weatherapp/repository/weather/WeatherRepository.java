package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.ForecastEntity;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<ForecastEntity> getForecast(String coordinates);

  void checkTemperatureUnit(ForecastEntity forecastEntity);
}

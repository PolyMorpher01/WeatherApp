package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<ForecastEntity> getForecast(String latlng);

  void checkTemperatureUnit(ForecastEntity forecastEntity);
}

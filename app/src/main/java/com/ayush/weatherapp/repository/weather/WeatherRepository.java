package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation);
}

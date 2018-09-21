package com.ayush.weatherapp.repository.forecast;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import io.reactivex.Observable;

public interface ForecastRepository {

  Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation);
}

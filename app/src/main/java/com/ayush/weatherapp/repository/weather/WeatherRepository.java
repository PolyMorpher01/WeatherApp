package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.ForecastEntity;
import com.ayush.weatherapp.realm.model.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<ForecastEntity> getForecast(String coordinates);

  void checkUnitConversion(ForecastEntity forecastEntity);
}

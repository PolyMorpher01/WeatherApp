package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.retrofit.weatherApi.pojo.Forecast;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<Forecast> getForecast(String coordinates);
}

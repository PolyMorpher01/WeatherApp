package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;

public interface WeatherRepository {

  Observable<ForecastDTO> getForecast(String coordinates);
}

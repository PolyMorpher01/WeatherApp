package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;
import io.realm.Realm;

public class LocalWeatherRepositoryImpl implements WeatherRepository {
  public LocalWeatherRepositoryImpl() {
  }

  @Override public Observable<ForecastDTO> getForecast(String coordinates) {
    return null;
  }

}

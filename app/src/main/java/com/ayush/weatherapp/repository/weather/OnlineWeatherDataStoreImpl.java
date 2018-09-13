package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import io.reactivex.Single;

public class OnlineWeatherDataStoreImpl implements WeatherDataStore {
  private WeatherAPIInterface weatherApiInterface;

  // TODO provide dependencies using dagger
  public OnlineWeatherDataStoreImpl() {
    weatherApiInterface = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Override public Single<Forecast> getForecast(String latlng) {
    return weatherApiInterface.getForecast(latlng).map(ForecastDTOtoRealmMapper::transform);
  }
}

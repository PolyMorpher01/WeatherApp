package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.realm.model.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import io.reactivex.Observable;

public class OnlineWeatherRepositoryImpl implements WeatherRepository {
  private WeatherAPIInterface weatherApiInterface;

  // TODO provide dependencies using dagger
  public OnlineWeatherRepositoryImpl() {
    weatherApiInterface = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Override public Observable<Forecast> getForecast(String coordinates) {
    return weatherApiInterface.getForecast(coordinates).map(ForecastDTOtoRealmMapper::transform);
  }
}

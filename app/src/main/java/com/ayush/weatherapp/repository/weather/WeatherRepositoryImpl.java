package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {
  private WeatherAPIInterface weatherApiInterface;

  // TODO provide dependencies using dagger
  public WeatherRepositoryImpl() {
    weatherApiInterface = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Override public Observable<ForecastDTO> getForecast(String coordinates) {
    return weatherApiInterface.getForecast(coordinates);
  }
}

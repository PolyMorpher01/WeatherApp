package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import io.reactivex.Observable;

public class OnlineWeatherRepositoryImpl implements WeatherRepository {
  private WeatherAPIInterface weatherApiInterface;

  // TODO provide dependencies using dagger
  public OnlineWeatherRepositoryImpl() {
    weatherApiInterface = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Override public Observable<ForecastEntity> getForecast(String latlng, boolean isCurrentLocation) {
    return weatherApiInterface.getForecast(latlng).map(ForecastDTOtoRealmMapper::transform).map(
        ForecastRealmToEntityMapper::transform);
  }
}

package com.ayush.weatherapp.repository.forecast;

import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import io.reactivex.Observable;

public class OnlineForecastRepositoryImpl implements ForecastRepository {
  private WeatherAPIInterface weatherApiInterface;

  // TODO provide dependencies using dagger
  public OnlineForecastRepositoryImpl() {
    weatherApiInterface = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Override
  public Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation) {
    return weatherApiInterface.getForecast(lat, lng).map(ForecastDTOtoRealmMapper::transform).map(
        ForecastRealmToEntityMapper::transform);
  }
}

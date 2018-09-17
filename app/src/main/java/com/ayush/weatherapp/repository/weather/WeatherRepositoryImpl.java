package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.constants.TemperatureUnit;
import com.ayush.weatherapp.entities.forecast.CurrentForecastEntity;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import com.ayush.weatherapp.mapper.ForecastRealmToEntityMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.utils.UnitConversionUtils;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.List;

//TODO rename weather to forecast
public class WeatherRepositoryImpl implements WeatherRepository {
  @Temperature private static int defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
  private WeatherDataStore onlineWeatherRepository;
  private WeatherDataStore localWeatherRepository;
  private PreferenceRepository preferenceRepository;

  public WeatherRepositoryImpl() {
    onlineWeatherRepository = new OnlineWeatherDataStoreImpl();
    localWeatherRepository = new LocalWeatherDataStoreImpl();
    preferenceRepository = PreferenceRepositoryImpl.get();
  }

  @Override public Observable<ForecastEntity> getForecast(String latlng) {
    return Observable.create(emitter -> {
      localWeatherRepository.getForecast(latlng)
          .map(forecast -> {
            //todo check if forecast is null

            //initialize value again
            defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
            return ForecastRealmToEntityMapper.transform(forecast);
          })
          .subscribe(emitter::onNext, throwable -> {
          });

      onlineWeatherRepository.getForecast(latlng)
          .doOnSuccess(this::saveWeatherForecast)
          .map(forecast -> {
            //initialize value again
            defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
            return ForecastRealmToEntityMapper.transform(forecast);
          })
          .subscribe(value -> {
            emitter.onNext(value);
            emitter.onComplete();
          }, emitter::onError);
    });
  }

  @Override public void checkTemperatureUnit(ForecastEntity forecast) {
    //TODO logic is not elegant
    if (preferenceRepository.getTemperatureUnit() != defaultTemperatureUnit) {
      convertCurrentTemperature(forecast.getCurrentForecastEntity());
      convertDailyData(forecast.getDailyForecastEntity().getDailyDataEntityList());
      convertHourlyData(forecast.getHourlyForecastEntity().getHourlyDataEntityList());

      defaultTemperatureUnit = preferenceRepository.getTemperatureUnit();
    }
  }

  private void convertDailyData(List<DailyDataEntity> dailyDatas) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      for (DailyDataEntity dailyData : dailyDatas) {
        dailyData.setWindSpeed(UnitConversionUtils.mphToKmph(dailyData.getWindSpeed()));
        dailyData.setTemperatureHigh(
            (int) UnitConversionUtils.fahrenheitToCelsius(dailyData.getTemperatureHigh()));
        dailyData.setTemperatureLow(
            (int) UnitConversionUtils.fahrenheitToCelsius(dailyData.getTemperatureLow()));
      }
    } else {
      for (DailyDataEntity dailyData : dailyDatas) {
        dailyData.setWindSpeed(UnitConversionUtils.kmphToMph(dailyData.getWindSpeed()));
        dailyData.setTemperatureHigh(
            (int) UnitConversionUtils.celsiusToFahrenheit(dailyData.getTemperatureHigh()));
        dailyData.setTemperatureLow(
            (int) UnitConversionUtils.celsiusToFahrenheit(dailyData.getTemperatureLow()));
      }
    }
  }

  private void convertHourlyData(List<HourlyDataEntity> hourlyDatas) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      for (HourlyDataEntity hourlyData : hourlyDatas) {
        hourlyData.setTemperature(
            (int) UnitConversionUtils.fahrenheitToCelsius(hourlyData.getTemperature()));
      }
    } else {
      for (HourlyDataEntity hourlyData : hourlyDatas) {
        hourlyData.setTemperature(
            (int) UnitConversionUtils.celsiusToFahrenheit(hourlyData.getTemperature()));
      }
    }
  }

  private void convertCurrentTemperature(CurrentForecastEntity currentForecast) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      currentForecast.setTemperature(
          (int) Math.round(
              UnitConversionUtils.fahrenheitToCelsius(currentForecast.getTemperature())));
    } else {
      currentForecast.setTemperature(
          (int) Math.round(
              UnitConversionUtils.celsiusToFahrenheit(currentForecast.getTemperature())));
    }
  }

  private void saveWeatherForecast(Forecast forecast) {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(forecast));
    realm.close();
  }
}

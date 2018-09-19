package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.constants.TemperatureUnit;
import com.ayush.weatherapp.entities.forecast.CurrentForecastEntity;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import com.ayush.weatherapp.mapper.ForecastEntityToRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.UnitConversionUtils;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.List;
import timber.log.Timber;

//TODO rename weather to forecast
public class WeatherRepositoryImpl implements WeatherRepository {
  @Temperature private static int defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
  private WeatherRepository onlineWeatherRepository;
  private WeatherRepository localWeatherRepository;
  private PreferenceRepository preferenceRepository;

  public WeatherRepositoryImpl() {
    onlineWeatherRepository = new OnlineWeatherRepositoryImpl();
    localWeatherRepository = new LocalWeatherRepositoryImpl();
    preferenceRepository = PreferenceRepositoryImpl.get();
  }

  @Override
  public Observable<ForecastEntity> getForecast(String latlng, boolean isCurrentLocation) {
    if (RealmUtils.isSavedLocally(Forecast.class) && isCurrentLocation) {
      return getLocalObservable(latlng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last row created was more than five minutes ago
            if (DateUtils.isFiveMinutesAgo(entity.getCreatedAt())) {
              return getOnlineObservable(latlng, isCurrentLocation);
            }
            return getLocalObservable(latlng, isCurrentLocation);
          });
    }
    return getOnlineObservable(latlng, isCurrentLocation);
  }

  public void checkTemperatureUnit(ForecastEntity forecast) {
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

  private Observable<ForecastEntity> getLocalObservable(String latlng,
      boolean isCurrentLocation) {
    return localWeatherRepository.getForecast(latlng, isCurrentLocation)
        //initialize value again
        .doOnNext(entity -> defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT);
  }

  private Observable<ForecastEntity> getOnlineObservable(String latlng,
      boolean isCurrentLocation) {
    return onlineWeatherRepository.getForecast(latlng, isCurrentLocation)
        //initialize value again
        .doOnNext(entity -> defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT)
        .map(entity -> {
          //save details of current location only
          if (isCurrentLocation) {
            saveWeatherForecast(ForecastEntityToRealmMapper.transform(entity));
          }
          return entity;
        });
  }

  private void saveWeatherForecast(Forecast forecast) {
    Realm realm = RealmUtils.getRealm();

    realm.executeTransaction(r -> {
          // delete all previously saved data and save new data to database
          List<Forecast> storedForecasts = realm.where(Forecast.class).findAll();
          if (storedForecasts != null) {
            for (Forecast storedForecast : storedForecasts) {
              storedForecast.removeFromRealm();
            }
          }
          r.insert(forecast);
        }
    );

    realm.close();
  }
}

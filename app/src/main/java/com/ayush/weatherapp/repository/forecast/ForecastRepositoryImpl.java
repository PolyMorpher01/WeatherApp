package com.ayush.weatherapp.repository.forecast;

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

public class ForecastRepositoryImpl implements ForecastRepository {
  @Temperature private static int defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
  private ForecastRepository onlineForecastRepository;
  private ForecastRepository localForecastRepository;
  private PreferenceRepository preferenceRepository;

  public ForecastRepositoryImpl() {
    onlineForecastRepository = new OnlineForecastRepositoryImpl();
    localForecastRepository = new LocalForecastRepositoryImpl();
    preferenceRepository = PreferenceRepositoryImpl.get();
  }

  @Override
  public Observable<ForecastEntity> getForecast(double lat, double lng, boolean isCurrentLocation) {
    if (RealmUtils.isSavedLocally(Forecast.class) && isCurrentLocation) {
      return getLocalObservable(lat, lng, isCurrentLocation)
          .flatMap(entity -> {
            //fetch from online repository if last row created was more than five minutes ago
            if (DateUtils.isFiveMinutesAgo(entity.getCreatedAt())) {
              return getOnlineObservable(lat, lng, isCurrentLocation);
            }
            return getLocalObservable(lat, lng, isCurrentLocation);
          });
    }
    return getOnlineObservable(lat, lng, isCurrentLocation);
  }

  private Observable<ForecastEntity> getLocalObservable(double lat, double lng,
      boolean isCurrentLocation) {
    return localForecastRepository.getForecast(lat, lng, isCurrentLocation)
        //initialize value again
        .doOnNext(entity -> defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT);
  }

  private Observable<ForecastEntity> getOnlineObservable(double lat, double lng,
      boolean isCurrentLocation) {
    return onlineForecastRepository.getForecast(lat, lng, isCurrentLocation)
        .doOnNext(entity -> {
          //initialize value again
          defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;

          //save details of current location only
          if (isCurrentLocation) {
            saveWeatherForecast(ForecastEntityToRealmMapper.transform(entity));
          }
        });
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

  private void saveWeatherForecast(Forecast forecast) {
    Realm realm = RealmUtils.getRealm();

    realm.executeTransaction(r -> {
          // delete all previously saved data and save new data to database
          List<Forecast> storedForecasts = r.where(Forecast.class).findAll();
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

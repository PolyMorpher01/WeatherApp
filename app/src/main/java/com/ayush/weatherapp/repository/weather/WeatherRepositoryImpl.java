package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.mapper.ForecastDTOtoRealmMapper;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import timber.log.Timber;

public class WeatherRepositoryImpl implements WeatherRepository {
  private WeatherRepository onlineWeatherRepository;
  private WeatherRepository localWeatherRepository;

  public WeatherRepositoryImpl() {
    onlineWeatherRepository = new OnlineWeatherRepositoryImpl();
    localWeatherRepository = new LocalWeatherRepositoryImpl();
  }

  @Override public Observable<ForecastDTO> getForecast(String coordinates) {
    //Observable<ForecastDTO> observable = Observable.create(emitter -> {
    //  emitter.onNext();
    //});
    //onlineWeatherRepository.getForecast(coordinates)
    //    .map(forecast -> {
    //      // save to local db
    //      return forecast;
    //    })
    //    .map(forecast -> {
    //      // DTO to entity (view model ) mapper and send
    //    }).

    onlineWeatherRepository.getForecast(coordinates)
        .map(forecastDTO -> {
          Forecast forecast = ForecastDTOtoRealmMapper.transform(forecastDTO);
          saveWeatherForecast(forecast);
          return forecast;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();

    return getRepository().getForecast(coordinates);
  }

  private WeatherRepository getRepository() {
/*    if (isSavedLocally()) {
      // todo check time
      Timber.e("inside");
      return localWeatherRepository;
    }*/
    Timber.e("outside");
    return onlineWeatherRepository;
  }

  private boolean isSavedLocally() {
    Realm realm = RealmUtils.getRealm();
    boolean isSaved = realm.where(Forecast.class).count() > 0;
    realm.close();
    return isSaved;
  }

  private void saveWeatherForecast(Forecast forecast) {
    removeAllWeatherForecast();

    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> realm.insert(forecast));
    realm.close();
  }

  private void removeAllWeatherForecast() {
    Realm realm = RealmUtils.getRealm();
    realm.executeTransaction(r -> r.deleteAll());
    realm.close();
  }
}

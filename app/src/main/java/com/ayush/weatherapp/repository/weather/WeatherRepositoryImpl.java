package com.ayush.weatherapp.repository.weather;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.Forecast;
import io.reactivex.Observable;
import io.realm.Realm;

public class WeatherRepositoryImpl implements WeatherRepository {
  private WeatherRepository onlineWeatherRepository;
  private WeatherRepository localWeatherRepository;

  public WeatherRepositoryImpl() {
    onlineWeatherRepository = new OnlineWeatherRepositoryImpl();
    localWeatherRepository = new LocalWeatherRepositoryImpl();
  }

  @Override public Observable<Forecast> getForecast(String coordinates) {
    return getRepository().getForecast(coordinates)
        .doOnNext(this::saveWeatherForecast);
  }

  private WeatherRepository getRepository() {
/*    if (isSavedLocally()) {
      // todo check time
      return localWeatherRepository;
    }*/
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

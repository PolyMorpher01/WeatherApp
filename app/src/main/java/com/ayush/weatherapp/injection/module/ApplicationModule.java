package com.ayush.weatherapp.injection.module;

import android.app.Application;
import com.ayush.weatherapp.home.HomeContract;
import com.ayush.weatherapp.home.HomePresenterImpl;
import com.ayush.weatherapp.repository.weather.LocalWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.OnlineWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.WeatherRepository;
import com.ayush.weatherapp.repository.weather.WeatherRepositoryImpl;
import dagger.Module;
import dagger.Provides;

@Module public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides WeatherRepository provideWeatherDataStore(
      LocalWeatherDataStoreImpl localWeatherDataStore,
      OnlineWeatherDataStoreImpl onlineWeatherDataStore) {
    return new WeatherRepositoryImpl(onlineWeatherDataStore, localWeatherDataStore);
  }

  @Provides LocalWeatherDataStoreImpl provideLocalWeatherDataStore() {
    return new LocalWeatherDataStoreImpl();
  }

  @Provides OnlineWeatherDataStoreImpl provideOnlineWeatherDataStore() {
    return new OnlineWeatherDataStoreImpl();
  }

}

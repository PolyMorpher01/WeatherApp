package com.ayush.weatherapp.injection.module;

import android.app.Application;
import com.ayush.weatherapp.repository.weather.LocalWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.OnlineWeatherDataStoreImpl;
import dagger.Module;
import dagger.Provides;

@Module public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides OnlineWeatherDataStoreImpl provideOnlineWeatherDataStore() {
    return new OnlineWeatherDataStoreImpl();
  }

  @Provides LocalWeatherDataStoreImpl provideLocalWeatherDataStore() {
    return new LocalWeatherDataStoreImpl();
  }
}

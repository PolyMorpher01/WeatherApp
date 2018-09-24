package com.ayush.weatherapp.injection.module;

import android.app.Application;
import android.content.Context;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.repository.weather.LocalWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.OnlineWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.WeatherRepository;
import com.ayush.weatherapp.repository.weather.WeatherRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides Context provideContext() {
    return mApplication;
  }

  @Provides WeatherRepository provideWeatherDataStore(
      LocalWeatherDataStoreImpl localWeatherDataStore,
      OnlineWeatherDataStoreImpl onlineWeatherDataStore,
      PreferenceRepository preferenceRepository) {
    return new WeatherRepositoryImpl(onlineWeatherDataStore, localWeatherDataStore,
        preferenceRepository);
  }

  @Provides LocalWeatherDataStoreImpl provideLocalWeatherDataStore() {
    return new LocalWeatherDataStoreImpl();
  }

  @Provides OnlineWeatherDataStoreImpl provideOnlineWeatherDataStore() {
    return new OnlineWeatherDataStoreImpl();
  }

  @Singleton @Provides PreferenceRepository providePreferenceRepository(Context context) {
    return new PreferenceRepositoryImpl(context);
  }
}

package com.ayush.weatherapp.injection.module;

import android.app.Application;
import android.content.Context;
import com.ayush.weatherapp.injection.annotations.ApplicationContext;
import com.ayush.weatherapp.repository.forecast.ForecastRepository;
import com.ayush.weatherapp.repository.forecast.ForecastRepositoryImpl;
import com.ayush.weatherapp.repository.forecast.LocalForecastRepositoryImpl;
import com.ayush.weatherapp.repository.forecast.RemoteForecastRepositoryImpl;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides @ApplicationContext Application provideApplication() {
    return mApplication;
  }

  @Provides Context provideContext() {
    return mApplication;
  }

  @Provides ForecastRepository provideWeatherDataStore(
      LocalForecastRepositoryImpl localForecastRepository,
      RemoteForecastRepositoryImpl onlineForecastRepository) {
    return new ForecastRepositoryImpl(localForecastRepository, onlineForecastRepository);
  }

  @Provides LocalForecastRepositoryImpl provideLocalWeatherRepository() {
    return new LocalForecastRepositoryImpl();
  }

  @Provides RemoteForecastRepositoryImpl provideOnlineWeatherRepository() {
    return new RemoteForecastRepositoryImpl();
  }

  @Singleton @Provides PreferenceRepository providePreferenceRepository(Context context) {
    return new PreferenceRepositoryImpl(context);
  }
}

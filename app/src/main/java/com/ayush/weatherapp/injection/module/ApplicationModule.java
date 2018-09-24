package com.ayush.weatherapp.injection.module;

import android.app.Application;
import android.content.Context;
import com.ayush.weatherapp.injection.annotations.ApplicationContext;
import com.ayush.weatherapp.repository.forecast.ForecastRepository;
import com.ayush.weatherapp.repository.forecast.ForecastRepositoryImpl;
import com.ayush.weatherapp.repository.forecast.LocalForecastRepositoryImpl;
import com.ayush.weatherapp.repository.forecast.RemoteForecastRepositoryImpl;
import com.ayush.weatherapp.repository.geocoding.GeocodingRepository;
import com.ayush.weatherapp.repository.geocoding.GeocodingRepositoryImpl;
import com.ayush.weatherapp.repository.geocoding.LocalGeocodingRepositoryImpl;
import com.ayush.weatherapp.repository.geocoding.RemoteGeocodingRepositoryImpl;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
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

  @Singleton @Provides PreferenceRepository providePreferenceRepository(Context context) {
    return new PreferenceRepositoryImpl(context);
  }

  @Provides ForecastRepository provideForecastRepository(
      LocalForecastRepositoryImpl localForecastRepository,
      RemoteForecastRepositoryImpl onlineForecastRepository) {
    return new ForecastRepositoryImpl(localForecastRepository, onlineForecastRepository);
  }

  @Provides LocalForecastRepositoryImpl provideLocalForecastRepository() {
    return new LocalForecastRepositoryImpl();
  }

  @Provides RemoteForecastRepositoryImpl provideOnlineForecastRepository(
      WeatherAPIInterface weatherAPIInterface) {
    return new RemoteForecastRepositoryImpl(weatherAPIInterface);
  }

  @Provides GeocodingRepository provideGeocodingRepository(
      LocalGeocodingRepositoryImpl localGeocodingRepository,
      RemoteGeocodingRepositoryImpl remoteGeocodingRepository) {
    return new GeocodingRepositoryImpl(localGeocodingRepository, remoteGeocodingRepository);
  }

  @Provides LocalGeocodingRepositoryImpl provideLocalGeocodingRepository() {
    return new LocalGeocodingRepositoryImpl();
  }

  @Provides RemoteGeocodingRepositoryImpl provideRemoteGeocodingRepository(
      GeocodingAPIInterface geocodingAPIInterface) {
    return new RemoteGeocodingRepositoryImpl(geocodingAPIInterface);
  }

  @Singleton @Provides WeatherAPIInterface getWeatherApiInterface() {
    return WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
  }

  @Singleton @Provides GeocodingAPIInterface getGeocodingApiInterface() {
    return GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);
  }
}

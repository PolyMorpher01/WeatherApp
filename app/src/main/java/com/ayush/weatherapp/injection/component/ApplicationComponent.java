package com.ayush.weatherapp.injection.component;

import com.ayush.weatherapp.injection.module.ApplicationModule;
import com.ayush.weatherapp.repository.weather.LocalWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.OnlineWeatherDataStoreImpl;
import com.ayush.weatherapp.repository.weather.WeatherRepositoryImpl;
import dagger.Component;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(WeatherRepositoryImpl weatherRepository);

  OnlineWeatherDataStoreImpl getOnlineWeatherDataStore();

  LocalWeatherDataStoreImpl getLocalWeatherDataStore();
}

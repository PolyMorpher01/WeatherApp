package com.ayush.weatherapp.injection.component;

import com.ayush.weatherapp.home.HomeActivity;
import com.ayush.weatherapp.injection.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(HomeActivity homeActivity);
}

package com.ayush.weatherapp.injection.component;

import com.ayush.weatherapp.injection.module.ActivityModule;
import com.ayush.weatherapp.injection.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  ActivityComponent plus(ActivityModule activityModule);
}

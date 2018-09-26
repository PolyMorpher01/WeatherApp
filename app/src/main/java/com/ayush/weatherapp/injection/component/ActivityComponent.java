package com.ayush.weatherapp.injection.component;

import com.ayush.weatherapp.home.HomeActivity;
import com.ayush.weatherapp.injection.module.ActivityModule;
import dagger.Subcomponent;

@Subcomponent(modules = { ActivityModule.class })
public interface ActivityComponent {
  void inject(HomeActivity homeActivity);
}

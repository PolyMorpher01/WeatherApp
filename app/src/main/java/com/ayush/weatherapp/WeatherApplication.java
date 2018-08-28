package com.ayush.weatherapp;

import android.app.Application;
import com.ayush.weatherapp.preferences.PreferenceRepositoryImpl;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class WeatherApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }

    PreferenceRepositoryImpl.init(getApplicationContext());
  }
}

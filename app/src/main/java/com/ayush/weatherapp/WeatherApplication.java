package com.ayush.weatherapp.core;

import android.app.Application;
import com.example.ayush.weatherapp.BuildConfig;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class MyApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
  }
}

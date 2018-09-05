package com.ayush.weatherapp;

import android.app.Application;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import io.realm.Realm;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class WeatherApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
    initializeRealm();
    PreferenceRepositoryImpl.init(this);
  }

  private void initializeRealm() {
    Realm.init(this);
  }
}

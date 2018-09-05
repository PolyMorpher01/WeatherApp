package com.ayush.weatherapp;

import android.app.Application;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
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

    initializeStetho();

    PreferenceRepositoryImpl.init(this);
  }

  private void initializeRealm() {
    Realm.init(this);
  }

  private void initializeStetho() {
    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
            .build());
  }
}

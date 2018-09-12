package com.ayush.weatherapp;

import android.app.Application;
import android.content.Context;
import com.ayush.weatherapp.injection.component.ApplicationComponent;
import com.ayush.weatherapp.injection.component.DaggerApplicationComponent;
import com.ayush.weatherapp.injection.module.ApplicationModule;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class WeatherApplication extends Application {

  private ApplicationComponent applicationComponent;

  public static WeatherApplication get(Context context) {
    return (WeatherApplication) context.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
    initializeRealm();

    initializeStetho();

    initApplicationComponent();

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

  public Application getApplicationObject() {
    return this;
  }

  public void initApplicationComponent() {
    if (applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}

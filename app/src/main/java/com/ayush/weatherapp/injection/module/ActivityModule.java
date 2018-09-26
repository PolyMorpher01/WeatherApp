package com.ayush.weatherapp.injection.module;

import android.app.Activity;
import android.content.Context;
import com.ayush.weatherapp.injection.annotations.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module public class ActivityModule {
  private Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides Activity providesActivity() {
    return activity;
  }

  @Provides @ActivityContext Context providesContext() {
    return activity;
  }
}

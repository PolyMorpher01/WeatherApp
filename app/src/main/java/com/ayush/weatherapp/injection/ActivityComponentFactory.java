package com.ayush.weatherapp.injection;

import android.app.Activity;
import com.ayush.weatherapp.WeatherApplication;
import com.ayush.weatherapp.injection.component.ActivityComponent;
import com.ayush.weatherapp.injection.module.ActivityModule;

public final class ActivityComponentFactory {
  private ActivityComponentFactory() {
  }

  public static ActivityComponent create(Activity activity) {
    return WeatherApplication.get(activity)
        .getApplicationComponent()
        .plus(new ActivityModule(activity));
  }
}

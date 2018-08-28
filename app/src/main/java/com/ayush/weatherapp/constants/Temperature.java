package com.ayush.weatherapp.constants;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TemperatureConstant {
  private static final int CELSIUS = 0;
  private static final int FAHRENHEIT = 1;

  @IntDef({CELSIUS, FAHRENHEIT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface Temperature {
    int CELSIUS = TemperatureConstant.CELSIUS;
    int FAHRENHEIT = TemperatureConstant.FAHRENHEIT;
  }
}

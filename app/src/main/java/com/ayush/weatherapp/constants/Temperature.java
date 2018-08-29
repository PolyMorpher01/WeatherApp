package com.ayush.weatherapp.constants;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Temperature {
  private static final int CELSIUS = 0;
  private static final int FAHRENHEIT = 1;

  @IntDef({CELSIUS, FAHRENHEIT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface Unit {
    int CELSIUS = Temperature.CELSIUS;
    int FAHRENHEIT = Temperature.FAHRENHEIT;
  }
}

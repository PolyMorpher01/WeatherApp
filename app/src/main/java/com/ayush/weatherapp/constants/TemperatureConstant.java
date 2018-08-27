package com.ayush.weatherapp.constants;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TemperatureConstant {
  public static final int CELSIUS = 0;
  public static final int FAHRENHEIT = 1;

  @IntDef({CELSIUS, FAHRENHEIT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface Temperature {
  }
}

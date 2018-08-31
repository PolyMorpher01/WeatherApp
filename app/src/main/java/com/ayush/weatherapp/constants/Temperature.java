package com.ayush.weatherapp.constants;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ayush.weatherapp.constants.TemperatureUnit.CELSIUS;
import static com.ayush.weatherapp.constants.TemperatureUnit.FAHRENHEIT;

@IntDef({ CELSIUS, FAHRENHEIT })
@Retention(RetentionPolicy.SOURCE)
public @interface Temperature {}


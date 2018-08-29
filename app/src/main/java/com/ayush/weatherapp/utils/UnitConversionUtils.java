package com.ayush.weatherapp.utils;

public final class UnitConversionUtils {
  private UnitConversionUtils() {
  }

  public static double fahrenheitToCelsius(double fahrenheit) {
    return ((fahrenheit - 32) * 5) / 9;
  }

  public static double celsiusToFahrenheit(double celsius) {
    return (celsius * 9 / 5) + 32;
  }

  public static double mphToKmph(double mph) {
    return 3.6 * mph;
  }

  public static double kmphToMph(double kmph) {
    return 0.277778 * kmph;
  }
}

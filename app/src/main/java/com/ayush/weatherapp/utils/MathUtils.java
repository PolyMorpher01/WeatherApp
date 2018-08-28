package com.ayush.weatherapp.utils;

public final class MathUtils {

  private MathUtils() {
  }

  public static double getAverage(double... numbers) {
    int sum = 0;
    for (double number : numbers) {
      sum += number;
    }
    return sum / numbers.length;
  }
}

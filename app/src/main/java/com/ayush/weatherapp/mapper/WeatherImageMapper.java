package com.ayush.weatherapp.utils;

import com.ayush.weatherapp.R;

public final class MapperUtils {
  public static int getWeatherImageResource(String icon) {

    switch (icon) {
      case "partly-cloudy-day":
        return R.drawable.img_cloudy_day;

      case "partly-cloudy-night":
        return R.drawable.img_cloudy_night;

      case "rain":
        return R.drawable.img_rainy;

      default:
        return R.drawable.img_no_connection;
    }
  }

  public static int getSmallWeatherImageResource(String icon) {
    switch (icon) {
      case "partly-cloudy-day":
        return R.drawable.img_small_cloudy;

      case "rain":
        return R.drawable.img_small_rainy;

      default:
        return R.drawable.img_small_partly_sunny;
    }
  }
}

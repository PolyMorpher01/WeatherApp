package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.WeatherImage;

public final class WeatherImageMapper {
  private WeatherImageMapper() {
  }

  public static int getImageResource(String weather) {

    switch (weather) {
      case WeatherImage.PARTLY_CLOUDY_DAY:
        return R.drawable.img_cloudy_day;

      case WeatherImage.PARTLY_CLOUDY_NIGHT:
        return R.drawable.img_cloudy_night;

      case WeatherImage.RAINY:
        return R.drawable.img_rainy;

      default:
        return R.drawable.img_no_connection;
    }
  }

  public static int getSmallImageResource(String weather) {
    switch (weather) {
      case WeatherImage.PARTLY_CLOUDY_DAY:
        return R.drawable.img_small_cloudy_dark;

      case WeatherImage.RAINY:
        return R.drawable.img_small_rainy_dark;

      default:
        return R.drawable.img_small_partly_sunny_dark;
    }
  }
}

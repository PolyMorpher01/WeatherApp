package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.WeatherImageConstant;

public final class WeatherImageMapper {
  private WeatherImageMapper() {
  }

  public static int getImageResource(String icon) {

    switch (icon) {
      case WeatherImageConstant.PARTLY_CLOUDY_DAY:
        return R.drawable.img_cloudy_day;

      case WeatherImageConstant.PARTLY_CLOUDY_NIGHT:
        return R.drawable.img_cloudy_night;

      case WeatherImageConstant.RAINY:
        return R.drawable.img_rainy;

      default:
        return R.drawable.img_no_connection;
    }
  }

  public static int getSmallImageResource(String icon) {
    switch (icon) {
      case WeatherImageConstant.PARTLY_CLOUDY_DAY:
        return R.drawable.img_small_cloudy;

      case WeatherImageConstant.RAINY:
        return R.drawable.img_small_rainy;

      default:
        return R.drawable.img_small_partly_sunny;
    }
  }
}

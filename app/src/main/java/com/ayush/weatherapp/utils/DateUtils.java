package com.ayush.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.isToday;

public final class DateUtils {

  private DateUtils() {
  }

  public static  String getDayOfTheWeek(int timeStamp) {

    if (isToday((long) timeStamp * 1000)) {
      return "Today";
    }

    Date date = new Date((long) timeStamp * 1000);

    return new SimpleDateFormat("EE", Locale.getDefault()).format(date);
  }

  public static String getTime(int timeStamp) {
    Date date = new Date((long) timeStamp * 1000);

    return new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(date);
  }
}

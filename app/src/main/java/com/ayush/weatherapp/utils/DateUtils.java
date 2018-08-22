package com.ayush.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.isToday;

public final class DateUtils {

  private static final String DAY_OF_THE_WEEK = "EE";
  private static final String HH_MM_AA = "hh:mm aa";
  private DateUtils() {
  }

  public static String getDayOfTheWeek(int timeStamp) {

    if (isToday((long) timeStamp * 1000)) {
      return "Today";
    }

    Date date = new Date((long) timeStamp * 1000);

    return new SimpleDateFormat(DAY_OF_THE_WEEK, Locale.getDefault()).format(date);
  }

  public static String getTime(int timeStamp) {
    Date date = new Date((long) timeStamp * 1000);

    return new SimpleDateFormat(HH_MM_AA, Locale.getDefault()).format(date);
  }
}

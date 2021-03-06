package com.ayush.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.isToday;

public final class DateUtils {
  private static final long FIVE_MINUTES = 5 * 60 * 1000;
  private static final String DAY_OF_THE_WEEK = "EE";
  private static final String HH_MM_AA = "hh:mm aa";

  private DateUtils() {
  }

  public static String getDayOfTheWeek(long timeStamp) {

    if (isToday(timeStamp * 1000)) {
      return "Today";
    }

    Date date = new Date(timeStamp * 1000);

    return new SimpleDateFormat(DAY_OF_THE_WEEK, Locale.getDefault()).format(date);
  }

  public static String getTime(long timeStamp) {
    Date date = new Date(timeStamp * 1000);

    return new SimpleDateFormat(HH_MM_AA, Locale.getDefault()).format(date);
  }

  public static long getCurrentTimeStamp() {
    return System.currentTimeMillis();
  }

  public static boolean isFiveMinutesAgo(long checkTime) {
    long fiveMinutesAgo = getCurrentTimeStamp() - FIVE_MINUTES;
    return checkTime < fiveMinutesAgo;
  }
}

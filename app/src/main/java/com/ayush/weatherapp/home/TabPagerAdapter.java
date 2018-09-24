package com.ayush.weatherapp.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import java.util.List;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
  private final static int NUMBER_OF_TABS = 2;
  private final static String TAB_TITLES[] = new String[] { "Daily", "Hourly" };
  private List<HourlyDataEntity> hourlyDatas;
  private List<DailyDataEntity> dailyDatas;

  TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return DailyForecastFragment.getInstance(dailyDatas);
    } else if (position == 1) {
      return HourlyForecastFragment.getInstance(hourlyDatas);
    }
    return null;
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return TAB_TITLES[position];
  }

  public void setDailyForecastData(List<DailyDataEntity> dailyDatas) {
    this.dailyDatas = dailyDatas;
  }

  public void setHourlyForecastData(List<HourlyDataEntity> hourlyDatas) {
    this.hourlyDatas = hourlyDatas;
  }
}

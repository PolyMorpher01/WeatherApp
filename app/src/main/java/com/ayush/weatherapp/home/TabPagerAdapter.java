package com.ayush.weatherapp.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyDataDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyDataDTO;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {
  private final static int NUMBER_OF_TABS = 2;
  private final static String TAB_TITLES[] = new String[] { "Daily", "Hourly" };
  private List<HourlyDataDTO> hourlyDatumDTOS;
  private List<DailyDataDTO> dailyDatumDTOS;

  TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return DailyForecastFragment.getInstance(dailyDatumDTOS);
    } else if (position == 1) {
      return HourlyForecastFragment.getInstance(hourlyDatumDTOS);
    }
    return null;
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return TAB_TITLES[position];
  }

  public void setDailyForecastData(List<DailyDataDTO> dailyDatumDTOS) {
    this.dailyDatumDTOS = dailyDatumDTOS;
  }

  public void setHourlyForecastData(List<HourlyDataDTO> hourlyDatumDTOS) {
    this.hourlyDatumDTOS = hourlyDatumDTOS;
  }
}

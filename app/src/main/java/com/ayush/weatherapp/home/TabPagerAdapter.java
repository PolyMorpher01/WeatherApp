package com.ayush.weatherapp.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {
  private final static int NUMBER_OF_TABS = 2;
  private String tabTitles[] = new String[] { "Daily", "Hourly" };
  private DailyForecastFragment dailyForecastFragment;
  private HourlyForecastFragment hourlyForecastFragment;

  TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void setForecastFragments(DailyForecastFragment dailyForecastFragment,
      HourlyForecastFragment hourlyForecastFragment) {
    this.dailyForecastFragment = dailyForecastFragment;
    this.hourlyForecastFragment = hourlyForecastFragment;
  }

  void setDailyForecastData(List<DailyForecast.DailyData> dailyForecastList) {
    dailyForecastFragment.setData(dailyForecastList);
  }

  void setHourlyForecastData(List<HourlyForecast.HourlyData> hourlyForeCastList) {
    hourlyForecastFragment.setData(hourlyForeCastList);
  }

  @Override public Fragment getItem(int position) {
    if (position == 0) {
      return dailyForecastFragment;
    } else if (position == 1) {
      return hourlyForecastFragment;
    }
    return null;
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return tabTitles[position];
  }
}

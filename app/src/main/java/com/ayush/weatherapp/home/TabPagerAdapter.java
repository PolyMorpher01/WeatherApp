package com.ayush.weatherapp.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {
  private final static int NUMBER_OF_TABS = 2;
  private String tabTitles[] = new String[] { "Tab1", "Tab2" };

  TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    Fragment fragment = null;
    if (position == 0) {
      fragment = new Fragment1();
    } else if (position == 1) {
      fragment = new Fragment2();
    }
    return fragment;
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return "Tab " + (position + 1);
  }
}

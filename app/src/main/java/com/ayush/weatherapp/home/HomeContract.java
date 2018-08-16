package com.ayush.weatherapp.home;

import com.ayush.weatherapp.mvp.BaseContract;

public interface HomeContract {
  interface View extends BaseContract.View {
    void setCurrentTemperatureSummary(String summary);
  }

  interface Presenter extends BaseContract.Presenter {
    View getView();

    void fetchWeatherDetails();
  }
}

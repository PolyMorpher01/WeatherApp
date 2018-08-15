package com.ayush.weatherapp.main;

import com.ayush.weatherapp.BasePresenter;

public interface MainContract {
  interface View {
    void setText(String s);
  }

  interface Presenter extends BasePresenter {
    void showProgressDialog();

    void hideProgressDialog();

    void setView(View view);

    void fetchWeatherDetail();
  }
}

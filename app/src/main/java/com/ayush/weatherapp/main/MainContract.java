package com.ayush.weatherapp.main;

import android.app.ProgressDialog;
import com.ayush.weatherapp.BasePresenter;

public interface MainContract {
  interface View {
    ProgressDialog getProgressDialog();

    void setCurrentTemperatureSummary(String s);
  }

  interface Presenter extends BasePresenter {
    void showProgressDialog();

    void hideProgressDialog();

    void setView(View view);

    void fetchWeatherDetails();
  }
}

package com.ayush.weatherapp.mvp;

public interface BaseContract {
  interface View {
    void showProgressDialog(String message);

    void hideProgressDialog();
  }

  interface Presenter {
  }
}

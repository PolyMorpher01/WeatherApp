package com.ayush.weatherapp.mvp;

import android.content.Context;

public interface BaseContract {
  interface View {
    Context getContext();

    void showProgressDialog(String message);

    void hideProgressDialog();
  }

  interface Presenter {
  }
}

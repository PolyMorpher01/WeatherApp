package com.ayush.weatherapp.mvp;

import android.content.Context;

public interface BaseContract {
  interface View {
    Context getContext();

    void showProgressDialog(String message, boolean cancelable);

    void hideProgressDialog();
  }

  interface Presenter {
    void attachView();
    void detachView();
  }
}

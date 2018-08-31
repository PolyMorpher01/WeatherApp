package com.ayush.weatherapp.mvp;

import android.content.Context;

public interface BaseContract {
  interface View {
    Context getContext();

    void showSwipeRefresh();

    void dismissSwipeRefresh();
  }

  interface Presenter {
    void attachView();
    void detachView();
  }
}

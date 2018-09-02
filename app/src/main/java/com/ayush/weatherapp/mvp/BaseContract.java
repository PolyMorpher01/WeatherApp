package com.ayush.weatherapp.mvp;

import android.content.Context;

public interface BaseContract {
  interface View {
    Context getContext();

    void showSwipeRefresh(boolean isShown);
  }

  interface Presenter {
    void attachView();

    void detachView();

    void onResume();

    void onPause();
  }
}

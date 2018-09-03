package com.ayush.weatherapp.mvp;

import android.content.Context;
import android.support.annotation.StringRes;

public class BasePresenterImpl<T extends BaseContract.BaseView>
    implements BaseContract.Presenter<T> {

  T view;

  @Override public void attachView(T view) {
    this.view = view;
  }

  @Override public void detachView() {

  }

  @Override public void onViewResume() {

  }

  @Override public void onViewPause() {

  }

  @Override public T getView() {
    return view;
  }

  @Override public Context getContext() {
    return view.getContext();
  }

  @Override public String getString(@StringRes int id, Object... args) {
    return getContext().getString(id, args);
  }
}

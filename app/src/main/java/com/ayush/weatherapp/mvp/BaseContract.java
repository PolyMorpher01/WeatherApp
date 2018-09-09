package com.ayush.weatherapp.mvp;

import android.content.Context;
import android.support.annotation.StringRes;
import io.reactivex.disposables.Disposable;

public interface BaseContract {

  interface BaseView {
    Context getContext();

    void showProgressBar(String message);

    void hideProgressBar();

    void onFailure(String message);
  }

  interface Presenter<T extends BaseView> {
    void attachView(T view);

    void detachView();

    void addSubscription(Disposable disposable);

    T getView();

    Context getContext();

    String getString(@StringRes int id, Object... args);
  }
}

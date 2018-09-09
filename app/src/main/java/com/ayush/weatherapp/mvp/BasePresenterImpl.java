package com.ayush.weatherapp.mvp;

import android.content.Context;
import android.support.annotation.StringRes;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenterImpl<T extends BaseContract.BaseView>
    implements BaseContract.Presenter<T> {

  private T view;

  private CompositeDisposable disposables;

  public BasePresenterImpl() {
    disposables = new CompositeDisposable();
  }

  @Override public void attachView(T view) {
    this.view = view;
  }

  @Override public void detachView() {
    disposables.dispose();
  }

  @Override public void addSubscription(Disposable disposable) {
    disposables.add(disposable);
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

package com.ayush.weatherapp.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import javax.inject.Inject;

public abstract class MVPBaseActivity<T extends BasePresenterImpl> extends BaseActivity
    implements BaseContract.BaseView {

  @Inject protected T presenter;

  public abstract void injectDagger();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDagger();
    presenter.attachView(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override public Context getContext() {
    return this;
  }
}

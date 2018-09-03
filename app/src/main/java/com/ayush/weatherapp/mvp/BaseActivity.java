package com.ayush.weatherapp.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import com.ayush.weatherapp.R;

public abstract class BaseActivity extends AppCompatActivity {
  private ActionBar actionBar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
  }

  protected abstract int getLayoutId();

  @SuppressWarnings("ConstantConditions") public void initToolbar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
  }

  public void showTitleBar(boolean showTitle) {
    actionBar.setDisplayShowTitleEnabled(showTitle);
  }
}

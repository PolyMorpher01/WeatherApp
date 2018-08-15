package com.ayush.weatherapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
  public ProgressDialog progressDialog;
  public ActionBar actionBar;
  private Toolbar toolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContextView());

    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);

    if (getToolbarResource() != -1) {
      initToolbar(getToolbarResource());
    }
  }

  protected abstract int getContextView();

  private void initToolbar(int toolbarId) {
    toolbar = findViewById(toolbarId);
    setSupportActionBar(toolbar);

    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
  }

  @NonNull Toolbar getToolbar() {
    if (toolbar == null) {
      throw new RuntimeException("Override getToolbarResource() with the toolbar id");
    }
    return toolbar;
  }

  protected int getToolbarResource() {
    return -1;
  }

  //getActionBar() method is already defined in android.app.activity so cannot override it
  @Nullable public ActionBar getCurrentActionBar() {
    return actionBar;
  }
}

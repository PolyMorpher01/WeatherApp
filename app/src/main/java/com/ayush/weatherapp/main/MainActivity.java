package com.ayush.weatherapp.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.ayush.weatherapp.BaseActivity;
import com.ayush.weatherapp.R;

public class MainActivity extends BaseActivity implements MainContract.View {

  @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.txt_main) TextView textView;

  ActionBar actionbar;

  MainContract.Presenter presenter;

  @Override protected int getContextView() {
    return R.layout.navigation_drawer;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);

    setActionBar();

    setNavigationView();

    presenter = new MainPresenter(this);
    presenter.setView(this);

    presenter.fetchWeatherDetail();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setActionBar() {
    setSupportActionBar(toolbar);

    actionbar = getSupportActionBar();

    actionbar.setDisplayShowTitleEnabled(false);
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
  }

  private void setNavigationView() {
    navigationView.setNavigationItemSelectedListener(menuItem -> {
      //TODO
      Toast.makeText(MainActivity.this, menuItem.toString(), Toast.LENGTH_SHORT).show();

      drawerLayout.closeDrawers();

      // Add code here to update the UI based on the item selected
      // For example, swap UI fragments here
      return true;
    });
  }

  @Override public void setText(String s) {
    textView.setText(s);
  }
}

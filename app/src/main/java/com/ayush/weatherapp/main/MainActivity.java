package com.ayush.weatherapp.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
  @BindView(R.id.txt_main) TextView txtCurrentForecastSummary;

  MainContract.Presenter presenter;

  @Override protected int getContextView() {
    return R.layout.navigation_drawer;
  }

  @Override protected int getToolbarResource() {
    return R.id.toolbar;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);

    setNavigationView();

    getCurrentActionBar().setDisplayShowTitleEnabled(false);

    presenter = new MainPresenter(this);
    presenter.setView(this);

    presenter.fetchWeatherDetails();
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

  @Override public ProgressDialog getProgressDialog() {
    return progressDialog;
  }

  @Override public void setCurrentTemperatureSummary(String s) {
    txtCurrentForecastSummary.setText(s);
  }
}

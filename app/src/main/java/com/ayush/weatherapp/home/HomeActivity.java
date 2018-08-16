package com.ayush.weatherapp.home;

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
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.mvp.BaseActivity;

public class HomeActivity extends BaseActivity implements HomeContract.View {

  @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.txt_main) TextView txtCurrentForecastSummary;
  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar toolbar;

  HomeContract.Presenter presenter;

  @Override protected int getContextView() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    //override theme defined in the xml for splash screen effect
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);

    initToolbar(toolbar);
    showTitleBar(false);

    setNavigationView();

    presenter = new HomePresenterImpl(this, this );

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
      Toast.makeText(HomeActivity.this, menuItem.toString(), Toast.LENGTH_SHORT).show();

      drawerLayout.closeDrawers();

      // Add code here to update the UI based on the item selected
      // For example, swap UI fragments here
      return true;
    });
  }

  @Override public void showProgressDialog(String message) {
    getProgressDialog().setMessage(message);
    getProgressDialog().show();
  }

  @Override public void hideProgressDialog() {
    getProgressDialog().dismiss();
  }

  @Override public void setCurrentTemperatureSummary(String summary) {
    txtCurrentForecastSummary.setText(summary);
  }
}

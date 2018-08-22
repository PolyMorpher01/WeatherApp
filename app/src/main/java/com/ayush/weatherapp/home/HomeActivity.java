package com.ayush.weatherapp.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.customViews.ForecastCompoundView;
import com.ayush.weatherapp.customViews.ForecastDetailCompoundView;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.mvp.BaseActivity;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.MathUtils;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class HomeActivity extends BaseActivity
    implements HomeContract.View, EasyPermissions.PermissionCallbacks {

  private static final int RC_LOCATION_PERM = 123;

  @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tv_location) TextView tvLocation;
  @BindView(R.id.tv_temperature_summary) TextView tvCurrentForecastSummary;
  @BindView(R.id.tv_temp_current) TextView tvTempCurrent;
  @BindView(R.id.iv_weather) ImageView ivWeather;
  @BindView(R.id.grp_list_forecast) LinearLayout grpListForecast;
  @BindView(R.id.detail_sun) ForecastDetailCompoundView detailSun;
  @BindView(R.id.detail_wind) ForecastDetailCompoundView detailWind;
  @BindView(R.id.detail_temperature) ForecastDetailCompoundView detailTemperature;

  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;

  TabPagerAdapter tabPagerAdapter;

  HomeContract.Presenter presenter;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    //override theme defined in the xml for splash screen effect
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);

    initToolbar(toolbar);
    showTitleBar(false);

    setNavigationView();

    presenter = new HomePresenterImpl(this);

    setTabLayout();

    checkLocationPermission();
  }

  private void setTabLayout() {
    tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(tabPagerAdapter);

    tabLayout.setupWithViewPager(viewPager);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

  @Override public Context getContext() {
    return this;
  }

  @Override public void showProgressDialog(String message, boolean cancelable) {
    getProgressDialog().setMessage(message);
    getProgressDialog().setCancelable(cancelable);
    getProgressDialog().show();
  }

  @Override public void hideProgressDialog() {
    getProgressDialog().dismiss();
  }

  @Override public void setCurrentForecast(CurrentForecast currentForecast) {
    tvCurrentForecastSummary.setText(currentForecast.getSummary());
    tvTempCurrent.setText(String.valueOf(Math.round(currentForecast.getTemperature())));
    ivWeather.setImageResource(
        WeatherImageMapper.getImageResource(currentForecast.getIcon()));
  }

  @Override public void setDailyForeCast(List<DailyForecast.DailyData> dailyForecastList) {
    for (DailyForecast.DailyData dailyData : dailyForecastList) {
      addDailyForecastView(dailyData);
    }

    DailyForecast.DailyData forecastDetailToday = dailyForecastList.get(0);

    setForecastDetails(forecastDetailToday);
  }

  @Override public void setLocality(String locality) {
    tvLocation.setText(locality);
  }

  private void addDailyForecastView(DailyForecast.DailyData dailyData) {
    String averageTemperature = String.valueOf(Math.round(
        MathUtils.getAverage(dailyData.getTemperatureHigh(),
            dailyData.getTemperatureLow())));

    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    ForecastCompoundView forecastCompoundView =
        (ForecastCompoundView) layoutInflater.inflate(R.layout.item_forecast_compound_view,
            grpListForecast, false);

    forecastCompoundView.setTopText(DateUtils.getDayOfTheWeek(dailyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(dailyData.getIcon()));
    forecastCompoundView.setBottomText(averageTemperature);

    grpListForecast.addView(forecastCompoundView, grpListForecast.getChildCount());
  }

  private void setForecastDetails(DailyForecast.DailyData todaysForecast) {

    detailSun.setTopText((String.valueOf(DateUtils.getTime(todaysForecast.getSunriseTime()))));
    detailSun.setBottomImage((DateUtils.getTime(todaysForecast.getSunsetTime())));

    detailWind.setBottomImage(String.valueOf(todaysForecast.getWindSpeed()));

    detailTemperature.setTopText(String.valueOf(Math.round(todaysForecast.getTemperatureHigh())));
    detailTemperature.setBottomImage(
        String.valueOf(Math.round(todaysForecast.getTemperatureLow())));
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // EasyPermissions handles the request result.
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  private boolean hasLocationPermission() {
    return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
  }

  @AfterPermissionGranted(RC_LOCATION_PERM)
  public void checkLocationPermission() {
    if (hasLocationPermission()) {
      presenter.fetchWeatherDetails();
    } else {
      EasyPermissions.requestPermissions(this, getString(R.string.request_permission_location),
          RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
    }
  }

  @Override
  public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    Timber.e("onPermissionsGranted:" + requestCode + ":" + perms.size());
    presenter.fetchWeatherDetails();
  }

  @Override
  public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    Timber.e("onPermissionsDenied:" + requestCode + ":" + perms.size());

    // Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    } else {
      finish();
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
      // Do something after user returned from app settings screen
      checkLocationPermission();
    }
  }
}

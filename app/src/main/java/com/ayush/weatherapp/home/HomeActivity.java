package com.ayush.weatherapp.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.customViews.ForecastDetailCompoundView;
import com.ayush.weatherapp.customViews.TemperatureTextView;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.mvp.BaseActivity;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.preferences.PreferenceRepository;
import com.ayush.weatherapp.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.UnitConversionUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class HomeActivity extends BaseActivity
    implements HomeContract.View, EasyPermissions.PermissionCallbacks {

  private static final int RC_LOCATION_PERM = 123;
  private static final int TODAY = 0;
  private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
  @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tv_location) TextView tvLocation;
  @BindView(R.id.tv_temperature_summary) TextView tvCurrentForecastSummary;
  @BindView(R.id.tv_temp_current) TemperatureTextView tvTempCurrent;
  @BindView(R.id.iv_weather) ImageView ivWeather;
  @BindView(R.id.detail_sun) ForecastDetailCompoundView detailSun;
  @BindView(R.id.detail_wind) ForecastDetailCompoundView detailWind;
  @BindView(R.id.detail_temperature) ForecastDetailCompoundView detailTemperature;
  @BindView(R.id.radio_celsius) RadioButton radioCelsius;
  @BindView(R.id.radio_fahrenheit) RadioButton radioFahrenheit;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.ll_content_frame) LinearLayout llContentFrame;
  private TabPagerAdapter tabPagerAdapter;
  private HomeContract.Presenter presenter;
  private PreferenceRepository preferenceRepository;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void setupPresenter() {
    presenter = new HomePresenterImpl(this);
  }

  @Override protected BaseContract.Presenter getPresenter() {
    return presenter;
  }

  @OnCheckedChanged({ R.id.radio_celsius, R.id.radio_fahrenheit }) void onChecked(
      CompoundButton button, boolean checked) {
    if (checked) {
      switch (button.getId()) {
        case R.id.radio_celsius:
          presenter.saveTemperatureUnitPref(Temperature.Unit.CELSIUS);
          break;
        case R.id.radio_fahrenheit:
          presenter.saveTemperatureUnitPref(Temperature.Unit.FAHRENHEIT);
          break;
      }
      drawerLayout.closeDrawers();
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    //override theme defined in the xml for splash screen effect
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);

    initToolbar(toolbar);
    showTitleBar(false);
    presenter = new HomePresenterImpl(this);
    tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
    preferenceRepository = PreferenceRepositoryImpl.get();
  }

  @Override protected void onResume() {
    super.onResume();
    checkLocationPermission();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override public void setRadioChecked() {
    if (preferenceRepository.getTemperatureUnit() == Temperature.Unit.CELSIUS) {
      radioCelsius.setChecked(true);
    } else {
      radioFahrenheit.setChecked(true);
    }
  }

  @Override public void setHomeBackground(int drawableId) {
    llContentFrame.setBackground(getResources().getDrawable(drawableId));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;

      case R.id.search:
        startPlaceAutoCompleteActivity();
        return true;
    }
    return super.onOptionsItemSelected(item);
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
    ivWeather.setImageResource(WeatherImageMapper.getImageResource(currentForecast.getIcon()));
  }

  @Override public void setDailyForeCast(List<DailyForecast.DailyData> dailyForecastList) {
    tabPagerAdapter.setDailyForecastData(dailyForecastList);

    //get forecast detail of today
    DailyForecast.DailyData forecastDetailToday = dailyForecastList.get(TODAY);
    setTodayForecastDetails(forecastDetailToday);
  }

  @Override public void setHourlyForeCast(List<HourlyForecast.HourlyData> hourlyForeCastList) {
    //show only 6 data
    final int MAX_NUMBER_OF_DATA = 6;
    tabPagerAdapter.setHourlyForecastData(
        new ArrayList<>(hourlyForeCastList.subList(0, MAX_NUMBER_OF_DATA)));
  }

  @Override public void setLocality(String locality) {
    tvLocation.setText(locality);
  }

  private void setTodayForecastDetails(DailyForecast.DailyData todaysForecast) {

    detailSun.setTopText((String.valueOf(DateUtils.getTime(todaysForecast.getSunriseTime()))));
    detailSun.setBottomText((DateUtils.getTime(todaysForecast.getSunsetTime())));

    int formatWind = R.string.format_wind_mph;
    double windSpeed = todaysForecast.getWindSpeed();
    double temperatureHigh = todaysForecast.getTemperatureHigh();
    double temperatureLow = todaysForecast.getTemperatureLow();

    if (preferenceRepository.getTemperatureUnit() == Temperature.Unit.CELSIUS) {
      formatWind = R.string.format_wind_kph;
      windSpeed = UnitConversionUtils.mphToKmph(windSpeed);
      temperatureHigh = UnitConversionUtils.fahrenheitToCelsius(temperatureHigh);
      temperatureLow = UnitConversionUtils.fahrenheitToCelsius(temperatureLow);
    }

    detailWind.setBottomText(getString(formatWind, windSpeed));
    detailTemperature.setTopText(
        "Max " + getString(R.string.format_temperature, Math.round(temperatureHigh)));
    detailTemperature.setBottomText(
        "Min " + getString(R.string.format_temperature, Math.round(temperatureLow)));
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
      fetchHomeDetails();
    } else {
      EasyPermissions.requestPermissions(this, getString(R.string.request_permission_location),
          RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
    }
  }

  @Override
  public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    fetchHomeDetails();
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

    //after returning from places autocomplete activity
    if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        Place place = PlaceAutocomplete.getPlace(this, data);
        LatLng latLng = place.getLatLng();
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        presenter.searchLocation(lat, lng);
      } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
        Status status = PlaceAutocomplete.getStatus(this, data);
        Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        Timber.e(status.getStatusMessage());
      }
    }
  }

  private void fetchHomeDetails() {
    presenter.initHome();
  }

  @Override public void setTabLayout() {
    viewPager.setAdapter(tabPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
  }

  @Override public void showGPSNotEnabledDialog(String title, String message) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setMessage(title);
    dialog.setPositiveButton(message,
        (dialogInterface, which) -> this.startActivity(
            new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
    dialog.setCancelable(false).show();
  }

  private void startPlaceAutoCompleteActivity() {
    try {
      Intent intent =
          new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
      startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
      Timber.e(e);
    }
  }
}

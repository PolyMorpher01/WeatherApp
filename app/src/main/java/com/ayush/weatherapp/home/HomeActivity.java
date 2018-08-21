package com.ayush.weatherapp.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.mvp.BaseActivity;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.LocationUtils;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.utils.MathUtils;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View {

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

  @Override public void setCurrentForecast(double latitude, double longitude,
      CurrentForecast currentForecast) {
    tvLocation.setText(LocationUtils.getLocality(latitude, longitude, this));
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
}

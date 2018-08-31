package com.ayush.weatherapp.home;

import android.support.annotation.DrawableRes;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import java.util.List;

public interface HomeContract {
  interface View extends BaseContract.View {
    void setCurrentForecast(CurrentForecast currentForecast);

    void setDailyForeCast(List<DailyForecast.DailyData> dailyForecastList);

    void setHourlyForeCast(List<HourlyForecast.HourlyData> hourlyForeCastList);

    void setAddress(String address);

    void setTabLayout();

    void showGPSNotEnabledDialog(String title, String message);

    void setRadioChecked();

    void setHomeBackground(@DrawableRes int drawableId);
  }

  interface Presenter extends BaseContract.Presenter {
    void onPause();

    void initHome();

    void onSwipeRefresh();

    void onCurrentLocationClicked();

    void saveTemperatureUnitPref(@Temperature int unit);

    void searchLocation(double lat, double lng);
  }
}

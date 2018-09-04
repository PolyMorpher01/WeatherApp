package com.ayush.weatherapp.home;

import android.support.annotation.DrawableRes;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyData;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import java.util.List;

public interface HomeContract {
  interface View extends BaseContract.BaseView {
    void setCurrentForecast(CurrentForecast currentForecast);

    void setCurrentTemperature(String temperature);

    void setDailyForeCast(List<DailyData> dailyForecastList);

    void setHourlyForeCast(List<HourlyForecast.HourlyData> hourlyForeCastList);

    void setAddress(String address);

    void setTabLayout();

    void showGPSNotEnabledDialog(String title, String message);

    void setHomeBackground(@DrawableRes int drawableId);

    void showErrorMessage();

    void changeErrorVisibility(boolean isError);

    void checkCelsiusButton(boolean check);

    void checkFahrenheitButton(boolean check);
  }

  interface Presenter {
    void initHome();

    void onViewRestart();

    void onSwipeRefresh();

    void onCurrentLocationClicked();

    void saveTemperatureUnitPref(@Temperature int unit);

    void searchLocation(double lat, double lng);
  }
}

package com.ayush.weatherapp.home;

import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.entities.forecast.CurrentForecastEntity;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import com.ayush.weatherapp.mvp.BaseContract;
import java.util.List;

public interface HomeContract {
  interface View extends BaseContract.BaseView {
    void setCurrentForecast(CurrentForecastEntity currentForecastDTO);

    void setCurrentTemperature(int temperature, @Temperature int tempUnit);

    void setDailyForeCast(List<DailyDataEntity> dailyForecastList);

    void setTodaysForecastDetail(DailyDataEntity dailyDataEntity, @Temperature int tempUnit);

    void setHourlyForeCast(List<HourlyDataEntity> hourlyForeCastList);

    void setAddress(String address);

    void setTabLayout();

    void showGPSNotEnabledDialog(String title, String message);

    void showErrorMessage();

    void changeErrorVisibility(boolean isError);

    void checkCelsiusButton(boolean check);

    void checkFahrenheitButton(boolean check);
  }

  interface Presenter {
    void initHome();

    void onViewPause();

    void onViewRestart();

    void onSwipeRefresh();

    void onCurrentLocationClicked();

    void saveTemperatureUnitPref(@Temperature int unit);

    void searchLocation(double lat, double lng);
  }
}

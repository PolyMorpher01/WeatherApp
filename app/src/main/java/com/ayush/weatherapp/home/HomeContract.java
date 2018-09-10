package com.ayush.weatherapp.home;

import android.support.annotation.DrawableRes;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.entities.CurrentForecastEntity;
import com.ayush.weatherapp.entities.DailyDataEntity;
import com.ayush.weatherapp.entities.HourlyDataEntity;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecastDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyDataDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyDataDTO;
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

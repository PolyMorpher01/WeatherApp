package com.ayush.weatherapp.home;

import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.mvp.BaseContract;
import java.util.List;

public interface HomeContract {
  interface View extends BaseContract.View {
    void setCurrentForecast(double latitude, double longitude,CurrentForecast currentForecast);

    void setDailyForeCast(List<DailyForecast.DailyData> dailyForecastList);

  }

  interface Presenter extends BaseContract.Presenter {
    View getView();

    void fetchWeatherDetails();
  }
}

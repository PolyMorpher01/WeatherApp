package com.ayush.weatherapp.home;

import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.ReverseGeoLocation;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import java.util.List;

public interface HomeContract {
  interface View extends BaseContract.View {
    void setCurrentForecast(CurrentForecast currentForecast);

    void setDailyForeCast(List<DailyForecast.DailyData> dailyForecastList);

    void setLocality(String locality);
  }

  interface Presenter extends BaseContract.Presenter {
    View getView();

    void fetchWeatherDetails();

    void fetchLocality(String requestString);
  }
}

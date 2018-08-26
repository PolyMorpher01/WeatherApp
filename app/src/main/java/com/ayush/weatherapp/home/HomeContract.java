package com.ayush.weatherapp.home;

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

    void setLocality(String locality);
    void setTabLayout();
  }

  interface Presenter extends BaseContract.Presenter {
    void fetchHomeDetails();
  }
}

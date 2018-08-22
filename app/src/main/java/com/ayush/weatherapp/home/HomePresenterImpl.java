package com.ayush.weatherapp.home;

import android.support.annotation.NonNull;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.Forecast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomePresenterImpl implements HomeContract.Presenter {

  private HomeContract.View view;

  HomePresenterImpl(BaseContract.View view) {
    this.view = (HomeContract.View) view;
  }

  @Override public void fetchWeatherDetails() {
    view.showProgressDialog("Loading", false);

    WeatherAPIInterface weatherApiInterface =
        WeatherAPIClient.getClient().create(WeatherAPIInterface.class);

    //TODO get coordinates based on a location
    final double TEST_LATITUDE = 37.8267;
    final double TEST_LONGITUDE = -122.4233;
    String requestString = TEST_LATITUDE + "," + TEST_LONGITUDE;

    Call<Forecast> forecastCall = weatherApiInterface.getForecast(requestString);

    forecastCall.enqueue(new Callback<Forecast>() {
      @Override
      public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
        Forecast forecast = response.body();

        CurrentForecast currentForecast = forecast.getCurrentForecast();

        DailyForecast dailyForecast = forecast.getDailyForecast();
        List<DailyForecast.DailyData> dailyForecastList = dailyForecast.getDailyDataList();

        view.setCurrentForecast(TEST_LATITUDE, TEST_LONGITUDE, currentForecast);

        view.setDailyForeCast(dailyForecastList);

        view.hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        Timber.e(t);
        Timber.e("Request Failed");
        view.hideProgressDialog();
      }
    });
  }

  @Override public void attachView() {
    
  }

  @Override public void detachView() {

  }
}

package com.ayush.weatherapp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import com.ayush.weatherapp.main.pojo.CurrentForecast;
import com.ayush.weatherapp.main.pojo.Forecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter implements MainContract.Presenter {

  private final Context context;

  MainContract.View view;

  MainPresenter(Context context) {
    this.context = context;
  }

  @Override public void showProgressDialog() {
    view.getProgressDialog().setMessage("Loading");
    view.getProgressDialog().show();
  }

  @Override public void hideProgressDialog() {
    view.getProgressDialog().dismiss();
  }

  @Override public void setView(MainContract.View view) {
    this.view = view;
  }

  @Override public void fetchWeatherDetails() {
    showProgressDialog();

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    //TODO get coordinates based on a location
    final double TEST_LATITUDE = 37.8267;
    final double TEST_LONGITUDE = -122.4233;
    String requestString = TEST_LATITUDE + "," + TEST_LONGITUDE;

    Call<Forecast> forecastCall = apiInterface.getForecast(requestString);

    forecastCall.enqueue(new Callback<Forecast>() {
      @Override
      public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
        Forecast forecast = response.body();

        CurrentForecast currentForecast = forecast.getCurrentForecast();

        view.setCurrentTemperatureSummary(currentForecast.getSummary());

        hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        Timber.e("Request Failed");
        hideProgressDialog();
      }
    });
  }
}

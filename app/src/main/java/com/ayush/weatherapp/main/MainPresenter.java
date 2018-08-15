package com.ayush.weatherapp.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import com.ayush.weatherapp.main.pojo.Currently;
import com.ayush.weatherapp.main.pojo.Forecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

  private static ProgressDialog progressDialog;
  private final Context context;

  MainContract.View view;

  MainPresenter(Context context) {
    this.context = context;
  }

  @Override public void showProgressDialog() {
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Loading");
    progressDialog.show();
  }

  @Override public void hideProgressDialog() {
    progressDialog.dismiss();
  }

  @Override public void setView(MainContract.View view) {
    this.view = view;
  }

  @Override public void fetchWeatherDetail() {
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

        Currently currently = forecast.getCurrently();

        view.setText(currently.getSummary());

        hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        call.cancel();
        hideProgressDialog();
      }
    });
  }
}

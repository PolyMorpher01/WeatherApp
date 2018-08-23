package com.ayush.weatherapp.home;

import android.support.annotation.NonNull;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.Address;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressComponents;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.ReverseGeoLocation;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIClient;
import com.ayush.weatherapp.retrofit.weatherApi.WeatherAPIInterface;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
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
    //TODO get coordinates based on a location
    final double TEST_LATITUDE = 37.8267;
    final double TEST_LONGITUDE = -122.4233;
    String requestString = TEST_LATITUDE + "," + TEST_LONGITUDE;

    fetchLocality(requestString);

    view.showProgressDialog("Loading", false);

    WeatherAPIInterface weatherApiInterface =
        WeatherAPIClient.getClient().create(WeatherAPIInterface.class);

    Call<Forecast> forecastCall = weatherApiInterface.getForecast(requestString);

    forecastCall.enqueue(new Callback<Forecast>() {
      @Override
      public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
        Forecast forecast = response.body();

        CurrentForecast currentForecast = forecast.getCurrentForecast();

        view.setCurrentForecast(currentForecast);

        DailyForecast dailyForecast = forecast.getDailyForecast();
        List<DailyForecast.DailyData> dailyForecastList = dailyForecast.getDailyDataList();
        view.setDailyForeCast(dailyForecastList);

        HourlyForecast hourlyForecast = forecast.getHourlyForecast();
        List<HourlyForecast.HourlyData> hourlyDataList = hourlyForecast.getHourlyDataList();
        view.setHourlyForeCast(hourlyDataList);

        view.hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        Timber.e(t);
        Timber.e("Request fetch forecast failed");
        view.hideProgressDialog();
      }
    });
  }

  @Override public void fetchLocality(String requestString) {

    final int LOCALITY_INDEX = 1;

    GeocodingAPIInterface geocodingAPIInterface =
        GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);

    Call<ReverseGeoLocation> reverseGeoLocationCall =
        geocodingAPIInterface.getLocationDetails(requestString);

    reverseGeoLocationCall.enqueue(new Callback<ReverseGeoLocation>() {
      String localityAddress;

      @Override
      public void onResponse(Call<ReverseGeoLocation> call, Response<ReverseGeoLocation> response) {
        ReverseGeoLocation reverseGeoLocation = response.body();
        List<Address> addressList = reverseGeoLocation.getAddresses();
        List<AddressComponents> addressComponentsList = addressList.get(0).getAddressComponents();

        localityAddress = addressComponentsList.get(LOCALITY_INDEX).getLongName();

        view.setLocality(localityAddress);
      }

      @Override public void onFailure(Call<ReverseGeoLocation> call, Throwable t) {
        Timber.e(t);
        Timber.e("Request fetch locality failed");
      }
    });
  }

  @Override public void attachView() {

  }

  @Override public void detachView() {

  }
}

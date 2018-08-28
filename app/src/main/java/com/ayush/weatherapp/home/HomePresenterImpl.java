package com.ayush.weatherapp.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.ayush.weatherapp.R;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomePresenterImpl implements HomeContract.Presenter {
  private final static String GEOCODING_API_KEY = "AIzaSyBKlS7jO0NvkX580X-ifkdfe12Mwzxhgc4";
  private final static int LOCATION_REQ_INTERVAL = 10000;
  private final static int FASTEST_LOCATION_REQ_INTERVAL = 5000;
  private final static int LOCALITY_INDEX = 1;

  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest locationRequest;
  private LocationCallback locationCallback;

  private HomeContract.View view;

  HomePresenterImpl(BaseContract.View view) {
    this.view = (HomeContract.View) view;
  }

  @Override public void attachView() {
  }

  @Override public void detachView() {
  }

  @Override public void fetchHomeDetails() {
    view.showProgressDialog("Loading", false);

    fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(getContext());

    if (isLocationServicesEnabled(getContext())) {
      fetchCurrentLocation();
    } else {
      view.showGPSNotEnabledDialog(
          getContext().getResources().getString(R.string.location_services_not_enabled),
          getContext().getResources().getString(R.string.open_location_settings));
    }
  }

  private void fetchCurrentLocation() {

    locationRequest = new LocationRequest();
    locationRequest.setInterval(LOCATION_REQ_INTERVAL);
    locationRequest.setFastestInterval(FASTEST_LOCATION_REQ_INTERVAL);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    locationCallback = new LocationCallback() {
      @Override public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
          Timber.e("location result null");
          return;
        }

        //get only first location
        Location currentLocation = locationResult.getLocations().get(0);
        String latLng =
            String.valueOf(currentLocation.getLatitude()) + "," + currentLocation.getLongitude();

        fetchLocality(latLng);
        fetchWeatherForecast(latLng);

        stopLocationUpdates();
      }
    };
    startLocationUpdates();
  }

  private void startLocationUpdates() {
    if (ActivityCompat.checkSelfPermission(getContext(),
        Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      throw new RuntimeException("Location permission not provided");
    }
    fusedLocationProviderClient.requestLocationUpdates(locationRequest,
        locationCallback,
        null /* Looper */);
  }

  private void stopLocationUpdates() {
    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
  }

  private void fetchLocality(String latLng) {
    GeocodingAPIInterface geocodingAPIInterface =
        GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);

    Call<ReverseGeoLocation> reverseGeoLocationCall =
        geocodingAPIInterface.getLocationDetails(latLng, GEOCODING_API_KEY);

    reverseGeoLocationCall.enqueue(new Callback<ReverseGeoLocation>() {
      String localityAddress;

      @Override
      public void onResponse(@NonNull Call<ReverseGeoLocation> call,
          @NonNull Response<ReverseGeoLocation> response) {
        ReverseGeoLocation reverseGeoLocation = response.body();

        List<Address> addressList = reverseGeoLocation.getAddresses();

        if (addressList != null && !addressList.isEmpty()) {
          List<AddressComponents> addressComponentsList = addressList.get(0).getAddressComponents();
          localityAddress = addressComponentsList.get(LOCALITY_INDEX).getLongName();
          view.setLocality(localityAddress);
        } else {
          view.setLocality("N/A");
        }
      }

      @Override
      public void onFailure(@NonNull Call<ReverseGeoLocation> call, @NonNull Throwable t) {
        Timber.e(t);
      }
    });
  }

  private void fetchWeatherForecast(String latLng) {

    WeatherAPIInterface weatherApiInterface =
        WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
    Call<Forecast> forecastCall = weatherApiInterface.getForecast(latLng);

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

        view.setTabLayout();

        view.hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        Timber.e(t);
        view.hideProgressDialog();
      }
    });
  }

  private boolean isLocationServicesEnabled(Context context) {
    LocationManager locationManager =
        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    boolean gpsEnabled;
    boolean networkEnabled;

    gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    return gpsEnabled || networkEnabled;
  }

  private Context getContext() {
    return view.getContext();
  }
}

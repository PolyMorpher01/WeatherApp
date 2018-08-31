package com.ayush.weatherapp.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.constants.WeatherImage;
import com.ayush.weatherapp.mvp.BaseContract;
import com.ayush.weatherapp.preferences.PreferenceRepository;
import com.ayush.weatherapp.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.Address;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressComponents;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocation;
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
  private static final int LOCATION_REQ_INTERVAL = 10000;
  private static final int FASTEST_LOCATION_REQ_INTERVAL = 5000;
  private static final String ADDRESS_STREET = "route";
  private static final String ADDRESS_CITY = "locality";
  private static final String ADDRESS_COUNTRY = "country";
  private static final String ADDRESS_ADMINISTRATIVE_AREA = "administrative_area_level_1";

  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest locationRequest;
  private LocationCallback locationCallback;

  private HomeContract.View view;
  private PreferenceRepository preferenceRepository;
  private GeocodingAPIInterface geocodingAPIInterface;

  private Forecast forecast;
  private CurrentForecast currentForecast;
  private DailyForecast dailyForecast;
  private List<DailyForecast.DailyData> dailyForecastList;
  private HourlyForecast hourlyForecast;
  private List<HourlyForecast.HourlyData> hourlyDataList;

  HomePresenterImpl(BaseContract.View view) {
    this.view = (HomeContract.View) view;

    geocodingAPIInterface = GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);

    preferenceRepository = PreferenceRepositoryImpl.get();
    preferenceRepository.onPreferenceChangeListener(newTemperature -> setForecastView());
  }

  @Override public void attachView() {
  }

  @Override public void detachView() {
  }

  @Override public void onPause() {
    stopLocationUpdates();
  }

  @Override public void initHome() {
    if (forecast != null) {
      return;
    }

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

    view.setRadioChecked();
  }

  @Override public void onCurrentLocationClicked() {
    fetchCurrentLocation();
  }

  @Override public void saveTemperatureUnitPref(@Temperature int unit) {
    preferenceRepository.saveTemperatureUnit(unit);
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
        String latLng = currentLocation.getLatitude() + "," + currentLocation.getLongitude();

        fetchAddress(latLng);
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
    if (locationCallback != null) {
      fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
  }

  private void fetchAddress(String latLng) {

    Call<GeoLocation> geoLocationCall =
        geocodingAPIInterface.getLocationDetails(latLng);

    geoLocationCall.enqueue(new Callback<GeoLocation>() {
      @Override
      public void onResponse(@NonNull Call<GeoLocation> call,
          @NonNull Response<GeoLocation> response) {
        GeoLocation geoLocation = response.body();
        List<Address> addressList = geoLocation.getAddresses();

        if (addressList != null && !addressList.isEmpty()) {
          List<AddressComponents> addressComponentsList = addressList.get(0).getAddressComponents();
          view.setAddress(getAddress(addressComponentsList));
        } else {
          view.setAddress(getContext().getResources().getString(R.string.not_available));
        }
      }

      @Override
      public void onFailure(@NonNull Call<GeoLocation> call, @NonNull Throwable t) {
        Timber.e(t);
      }
    });
  }

  @Override public void searchLocation(double lat, double lng) {
    String latlng = lat + "," + lng;
    fetchWeatherForecast(latlng);
    fetchAddress(latlng);
  }

  private String getAddress(List<AddressComponents> addressComponentsList) {
    String primaryAddress = getAddressPrimary(addressComponentsList);
    String secondaryAddress = getAddressSecondary(addressComponentsList);
    String address = "";

    if (!TextUtils.isEmpty(primaryAddress)) {
      address = primaryAddress;
      if (!TextUtils.isEmpty(secondaryAddress)) {
        address += ", " + secondaryAddress;
      }
      return address;
    }

    //case when primary address is empty
    if (!TextUtils.isEmpty(secondaryAddress)) {
      address = secondaryAddress;
      return address;
    }
    return address;
  }

  private String getAddressPrimary(List<AddressComponents> addressComponentsList) {
    for (AddressComponents addressComponent : addressComponentsList) {
      if (addressComponent.getTypes().contains(ADDRESS_STREET)) {
        return addressComponent.getLongName();
      }
    }
    return "";
  }

  private String getAddressSecondary(List<AddressComponents> addressComponentsList) {
    for (AddressComponents addressComponent : addressComponentsList) {
      if (addressComponent.getTypes().contains(ADDRESS_CITY)) {
        return addressComponent.getLongName();
      }
      if (addressComponent.getTypes().contains(ADDRESS_ADMINISTRATIVE_AREA)) {
        return addressComponent.getLongName();
      }
      if (addressComponent.getTypes().contains(ADDRESS_COUNTRY)) {
        return addressComponent.getLongName();
      }
    }
    return "";
  }

  private void fetchWeatherForecast(String latLng) {

    WeatherAPIInterface weatherApiInterface =
        WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
    Call<Forecast> forecastCall = weatherApiInterface.getForecast(latLng);

    forecastCall.enqueue(new Callback<Forecast>() {
      @Override
      public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
        forecast = response.body();
        currentForecast = forecast.getCurrentForecast();
        dailyForecast = forecast.getDailyForecast();
        dailyForecastList = dailyForecast.getDailyDataList();
        hourlyForecast = forecast.getHourlyForecast();
        hourlyDataList = hourlyForecast.getHourlyDataList();
        setForecastView();
        view.hideProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        Timber.e(t);
        view.hideProgressDialog();
      }
    });
  }

  private void setForecastView() {
    view.setCurrentForecast(currentForecast);
    view.setDailyForeCast(dailyForecastList);
    view.setHourlyForeCast(hourlyDataList);
    view.setTabLayout();

    changeHomeBackground();
  }

  private void changeHomeBackground() {
    switch (currentForecast.getIcon()) {
      case WeatherImage.CLEAR_DAY:
        view.setHomeBackground(R.drawable.background_gradient_sunny);
        break;

      case WeatherImage.RAINY:
      case WeatherImage.SNOW:
        view.setHomeBackground(R.drawable.background_gradient_rainy);
        break;

      case WeatherImage.CLOUDY:
      case WeatherImage.PARTLY_CLOUDY_DAY:
        view.setHomeBackground(R.drawable.background_gradient_cloudy);
        break;

      default:
        view.setHomeBackground(R.drawable.background_gradient_default);
    }
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

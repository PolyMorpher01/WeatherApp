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
import com.ayush.weatherapp.mvp.BasePresenterImpl;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.repository.weather.WeatherRepository;
import com.ayush.weatherapp.repository.weather.WeatherRepositoryImpl;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIClient;
import com.ayush.weatherapp.retrofit.geocodingApi.GeocodingAPIInterface;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.Address;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressComponents;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocation;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.Forecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

//public class WorkOrderDetailPresenterImpl extends BasePresenter<WorkOrderDetailContract.BaseView>
//    implements WorkOrderDetailContract.Presenter, OnMapReadyCallback {

public class HomePresenterImpl extends BasePresenterImpl<HomeContract.View>
    implements HomeContract.Presenter {

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
  private WeatherRepository weatherRepository;

  // TODO dagger
  public HomePresenterImpl(BaseContract.BaseView baseView) {
    this.view = (HomeContract.View) baseView;
  }

  @Override public void attachView(HomeContract.View view) {
    super.attachView(view);
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    geocodingAPIInterface = GeocodingAPIClient.getClient().create(GeocodingAPIInterface.class);

    preferenceRepository = PreferenceRepositoryImpl.get();
    // todo Ayush fix this
    //preferenceRepository.onPreferenceChangeListener(newTemperature -> setForecastView());
    weatherRepository = new WeatherRepositoryImpl();
  }

  @Override public void onViewPause() {
    stopLocationUpdates();
  }

  @Override public void initHome() {
    view.showProgressBar("");
    fetchByCurrentLocation();
    view.setRadioChecked();
    Timber.e(this.toString());
  }

  @Override public void onViewRestart() {
    if (forecast != null) {
      return;
    }
    initHome();
  }

  @Override public void onSwipeRefresh() {
    fetchByGivenLocation(preferenceRepository.getCurrentLocationCoordinates());
  }

  @Override public void onCurrentLocationClicked() {
    view.showProgressBar("");
    fetchByCurrentLocation();
  }

  @Override public void saveTemperatureUnitPref(@Temperature int unit) {
    preferenceRepository.saveTemperatureUnit(unit);
  }

  private void fetchByCurrentLocation() {
    if (isLocationServicesEnabled()) {
      initLocationRequest();
      startLocationUpdates();
    }
  }

  private void initLocationRequest() {
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
  }

  private void startLocationUpdates() {
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      throw new RuntimeException("Location permission not provided");
    }
    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
  }

  private void stopLocationUpdates() {
    if (locationCallback != null) {
      fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
  }

  private void fetchAddress(String latLng) {

    Call<GeoLocation> geoLocationCall = geocodingAPIInterface.getLocationDetails(latLng);

    geoLocationCall.enqueue(new Callback<GeoLocation>() {
      @Override public void onResponse(@NonNull Call<GeoLocation> call,
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

      @Override public void onFailure(@NonNull Call<GeoLocation> call, @NonNull Throwable t) {
        Timber.e(t);
      }
    });
  }

  @Override public void searchLocation(double lat, double lng) {
    String latlng = lat + "," + lng;
    fetchByGivenLocation(latlng);
  }

  private void fetchByGivenLocation(String latlng) {
    if (isLocationServicesEnabled()) {
      fetchWeatherForecast(latlng);
      fetchAddress(latlng);
    }
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
    view.showProgressBar("");

    // TODO refactor after mvp complete
    Disposable disposable = weatherRepository.getForecast(latLng)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<Forecast>() {
          @Override public void onNext(Forecast forecast) {
            currentForecast = forecast.getCurrentForecast();
            dailyForecast = forecast.getDailyForecast();
            dailyForecastList = dailyForecast.getDailyDataList();
            hourlyForecast = forecast.getHourlyForecast();
            hourlyDataList = hourlyForecast.getHourlyDataList();
            setForecastView();
            view.changeErrorVisibility(false);
            //save to provide coordinates during refresh
            preferenceRepository.saveCurrentLocationCoordinates(latLng);
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
            view.changeErrorVisibility(true);
            view.showErrorMessage();
            view.hideProgressBar();
          }

          @Override public void onComplete() {
            view.hideProgressBar();
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

  private boolean isLocationServicesEnabled() {
    LocationManager locationManager =
        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    boolean gpsEnabled;
    boolean networkEnabled;

    gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    if (!gpsEnabled && !networkEnabled) {
      view.showGPSNotEnabledDialog(
          getContext().getResources().getString(R.string.location_services_not_enabled),
          getContext().getResources().getString(R.string.open_location_settings));
      return false;
    }
    return true;
  }
}

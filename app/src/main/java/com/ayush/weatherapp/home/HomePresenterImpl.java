package com.ayush.weatherapp.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.constants.TemperatureUnit;
import com.ayush.weatherapp.entities.forecast.CurrentForecastEntity;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.mvp.BasePresenterImpl;
import com.ayush.weatherapp.repository.forecast.ForecastRepository;
import com.ayush.weatherapp.repository.geocoding.GeocodingRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.utils.UnitConversionUtils;
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
import javax.inject.Inject;
import timber.log.Timber;

public class HomePresenterImpl extends BasePresenterImpl<HomeContract.View>
    implements HomeContract.Presenter {
  private static final int LOCATION_REQ_INTERVAL = 10000;
  private static final int FASTEST_LOCATION_REQ_INTERVAL = 5000;
  @Temperature private int defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;
  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest locationRequest;
  private LocationCallback locationCallback;

  private PreferenceRepository preferenceRepository;
  private ForecastEntity forecast;

  private ForecastRepository forecastRepository;
  private GeocodingRepository geocodingRepository;

  @Inject public HomePresenterImpl(ForecastRepository forecastRepository,
      PreferenceRepository preferenceRepository, GeocodingRepository geocodingRepository) {
    this.forecastRepository = forecastRepository;
    this.preferenceRepository = preferenceRepository;
    this.geocodingRepository = geocodingRepository;
  }

  @Override public void attachView(HomeContract.View view) {
    super.attachView(view);
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    preferenceRepository.onPreferenceChangeListener(newTemperature -> setForecastView());
  }

  @Override public void onViewPause() {
    stopLocationUpdates();
  }

  @Override public void initHome() {
    getView().showProgressBar("");
    fetchByCurrentLocation();
    setTemperatureUnit();
  }

  private void setTemperatureUnit() {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      getView().checkCelsiusButton(true);
    } else {
      getView().checkFahrenheitButton(true);
    }
  }

  @Override public void onViewRestart() {
    if (forecast != null) {
      return;
    }
    initHome();
  }

  @Override public void onSwipeRefresh() {
    fetchByGivenLocation(preferenceRepository.getLatitude(), preferenceRepository.getLongitude());
  }

  @Override public void onCurrentLocationClicked() {
    getView().showProgressBar("");
    fetchByCurrentLocation();
  }

  @Override public void saveTemperatureUnitPref(@Temperature int unit) {
    preferenceRepository.saveTemperatureUnit(unit);
  }

  private void fetchByCurrentLocation() {
    if (isLocationServicesEnabled()) {
      getLastKnownLocation();
    }
  }

  private void getLastKnownLocation() {
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      throw new RuntimeException("Location permission not provided");
    }
    fusedLocationProviderClient.getLastLocation()
        .addOnSuccessListener((Activity) getContext(), location -> {
          if (location == null) {
            initLocationRequest();
            startLocationUpdates();
          } else {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            fetchAddress(lat, lng, true);
            fetchWeatherForecast(lat, lng, true);
          }
        });
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

        double lat = currentLocation.getLatitude();
        double lng = currentLocation.getLongitude();

        fetchAddress(lat, lng, true);
        fetchWeatherForecast(lat, lng, true);

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

  private void fetchAddress(double lat, double lng, boolean isCurrentLocation) {
    Disposable disposable = geocodingRepository.getLocation(lat, lng, isCurrentLocation)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<GeolocationEntity>() {
          @Override public void onNext(GeolocationEntity geoLocation) {
            setLocation(geoLocation);
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);

            getView().setAddress(getString(R.string.not_available));
          }

          @Override public void onComplete() {
          }
        });

    addSubscription(disposable);
  }

  @Override public void searchLocation(double lat, double lng) {
    fetchByGivenLocation(lat, lng);
  }

  private void fetchByGivenLocation(double lat, double lng) {
    if (isLocationServicesEnabled()) {
      fetchWeatherForecast(lat, lng, false);
      fetchAddress(lat, lng, false);
    }
  }

  private void setLocation(GeolocationEntity geoLocation) {
    getView().setAddress(geoLocation.getLocation());
  }

  private void fetchWeatherForecast(double lat, double lng, boolean isCurrentLocation) {
    Disposable disposable = forecastRepository.getForecast(lat, lng, isCurrentLocation)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(d -> getView().showProgressBar(""))
        .subscribeWith(new DisposableObserver<ForecastEntity>() {
          @Override public void onNext(ForecastEntity forecast) {
            //initialize value
            defaultTemperatureUnit = TemperatureUnit.FAHRENHEIT;

            setForecast(forecast, lat, lng);

            //save to provide coordinates during refresh
            preferenceRepository.saveLatitude(lat);
            preferenceRepository.saveLongitude(lng);
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
            getView().onFailure("Error fetching new data");
            getView().hideProgressBar();
          }

          @Override public void onComplete() {
            getView().hideProgressBar();
          }
        });

    addSubscription(disposable);
  }

  //todo refactor
  private void setForecast(ForecastEntity forecast, double lat, double lng) {
    this.forecast = forecast;
    setForecastView();
    getView().changeErrorVisibility(false);
  }

  private void setForecastView() {
    if (forecast == null) {
      return;
    }

    checkTemperatureUnit(forecast);
    getView().setDailyForeCast(forecast.getDailyForecastEntity().getDailyDataEntityList());
    getView().setHourlyForeCast(forecast.getHourlyForecastEntity().getHourlyDataEntityList());
    getView().setCurrentForecast(forecast.getCurrentForecastEntity());
    getView().setCurrentTemperature(forecast.getCurrentForecastEntity().getTemperature(),
        preferenceRepository.getTemperatureUnit());
    getView().setTodaysForecastDetail(forecast.getDailyForecastEntity().getTodaysDataEntity(),
        preferenceRepository.getTemperatureUnit());
    getView().setTabLayout();
  }

  private boolean isLocationServicesEnabled() {
    LocationManager locationManager =
        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    boolean gpsEnabled;
    boolean networkEnabled;

    gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    if (!gpsEnabled && !networkEnabled) {
      getView().showGPSNotEnabledDialog(getString(R.string.location_services_not_enabled),
          getString(R.string.open_location_settings));
      return false;
    }
    return true;
  }

  public void checkTemperatureUnit(ForecastEntity forecast) {
    //TODO logic is not elegant
    if (preferenceRepository.getTemperatureUnit() != defaultTemperatureUnit) {
      convertCurrentTemperature(forecast.getCurrentForecastEntity());
      convertDailyData(forecast.getDailyForecastEntity().getDailyDataEntityList());
      convertHourlyData(forecast.getHourlyForecastEntity().getHourlyDataEntityList());

      defaultTemperatureUnit = preferenceRepository.getTemperatureUnit();
    }
  }

  private void convertDailyData(List<DailyDataEntity> dailyDatas) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      for (DailyDataEntity dailyData : dailyDatas) {
        dailyData.setTemperatureHigh(
            (int) UnitConversionUtils.fahrenheitToCelsius(dailyData.getTemperatureHigh()));
        dailyData.setTemperatureLow(
            (int) UnitConversionUtils.fahrenheitToCelsius(dailyData.getTemperatureLow()));
      }
    } else {
      for (DailyDataEntity dailyData : dailyDatas) {
        dailyData.setTemperatureHigh(
            (int) UnitConversionUtils.celsiusToFahrenheit(dailyData.getTemperatureHigh()));
        dailyData.setTemperatureLow(
            (int) UnitConversionUtils.celsiusToFahrenheit(dailyData.getTemperatureLow()));
      }
    }
  }

  private void convertHourlyData(List<HourlyDataEntity> hourlyDatas) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      for (HourlyDataEntity hourlyData : hourlyDatas) {
        hourlyData.setTemperature(
            (int) UnitConversionUtils.fahrenheitToCelsius(hourlyData.getTemperature()));
      }
    } else {
      for (HourlyDataEntity hourlyData : hourlyDatas) {
        hourlyData.setTemperature(
            (int) UnitConversionUtils.celsiusToFahrenheit(hourlyData.getTemperature()));
      }
    }
  }

  private void convertCurrentTemperature(CurrentForecastEntity currentForecast) {
    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      currentForecast.setTemperature(
          (int) Math.round(
              UnitConversionUtils.fahrenheitToCelsius(currentForecast.getTemperature())));
    } else {
      currentForecast.setTemperature(
          (int) Math.round(
              UnitConversionUtils.celsiusToFahrenheit(currentForecast.getTemperature())));
    }
  }
}

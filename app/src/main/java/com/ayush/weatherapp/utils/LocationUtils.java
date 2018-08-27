package com.ayush.weatherapp.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import com.ayush.weatherapp.R;

public final class LocationUtils {

  private LocationUtils() {
  }

  //Reference: https://stackoverflow.com/questions/10311834/how-to-check-if-location-services-are-enabled
  public static void checkLocationServices(Context context) {
    LocationManager locationManager =
        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    boolean gpsEnabled;
    boolean networkEnabled;

    gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    if (!gpsEnabled && !networkEnabled) {
      // notify user
      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
      dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
      dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings),
          (paramDialogInterface, paramInt) -> {
            //get gps
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
          });
      dialog.setCancelable(false).show();
    }
  }
}

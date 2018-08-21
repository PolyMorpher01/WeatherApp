package com.ayush.weatherapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.ayush.weatherapp.R;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public final class LocationUtils {

  public static String getLocality(double latitude, double longitude, Context context) {

    Geocoder geocoder = new Geocoder(context, Locale.getDefault());

    List<Address> addresses;
    try {
      addresses = geocoder.getFromLocation(latitude, longitude, 1);
      return addresses.get(0).getLocality();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return context.getString(R.string.location_not_available);
  }
}

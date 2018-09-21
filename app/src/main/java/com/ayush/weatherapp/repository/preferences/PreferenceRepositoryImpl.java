package com.ayush.weatherapp.repository.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.ayush.weatherapp.constants.TemperatureUnit;

public final class PreferenceRepositoryImpl implements PreferenceRepository {

  private static final String APP_PREF_NAME = "APP_PREF_NAME";
  private static final String TEMP_UNIT = "TEMP_UNIT";
  private static final String CURRENT_LATITUDE = "CURRENT_LATITUDE";
  private static final String CURRENT_LONGITUDE = "CURRENT_LONGITUDE";

  private static PreferenceRepositoryImpl preferenceRepository;
  private SharedPreferences sharedPreferences;
  private PreferenceChangeListener preferenceChangeListener;
  private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

  private PreferenceRepositoryImpl(Context context) {
    sharedPreferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    sharedPreferenceChangeListener = (sharedPreferences, key) -> {
      if (key.equals(TEMP_UNIT)) {
        preferenceChangeListener.onTemperatureChanged(0);
      }
    };
    sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
  }

  public static void init(Context context) {
    preferenceRepository = new PreferenceRepositoryImpl(context);
  }

  public static PreferenceRepository get() {
    return preferenceRepository;
  }

  @Override public void saveTemperatureUnit(int value) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(TEMP_UNIT, value);
    editor.apply();
  }

  @Override public int getTemperatureUnit() {
    return sharedPreferences.getInt(TEMP_UNIT, TemperatureUnit.CELSIUS);
  }

  @Override public void saveLatitude(double lat) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    //shared preferences does not support putDouble
    editor.putLong(CURRENT_LATITUDE, Double.doubleToRawLongBits(lat));
    editor.apply();
  }

  @Override public void saveLongitude(double lng) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    //shared preferences does not support putDouble
    editor.putLong(CURRENT_LONGITUDE, Double.doubleToRawLongBits(lng));
    editor.apply();
  }

  @Override public double getLatitude() {
    return Double.longBitsToDouble(sharedPreferences.getLong(CURRENT_LATITUDE, 0));
  }

  @Override public double getLongitude() {
    return Double.longBitsToDouble(sharedPreferences.getLong(CURRENT_LONGITUDE, 0));
  }

  @Override public void onPreferenceChangeListener(PreferenceChangeListener changeListener) {
    preferenceChangeListener = changeListener;
  }
}

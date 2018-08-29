package com.ayush.weatherapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.ayush.weatherapp.constants.Temperature;

public final class PreferenceRepositoryImpl implements PreferenceRepository {

  private static final String APP_PREF_NAME = "APP_PREF_NAME";
  private static final String TEMP_UNIT = "TEMP_UNIT";

  private static PreferenceRepositoryImpl preferenceRepository;
  private SharedPreferences sharedPreferences;
  private PreferenceChangeListener preferenceChangeListener;
  private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

  private PreferenceRepositoryImpl(Context context) {
    sharedPreferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    sharedPreferenceChangeListener = (sharedPreferences, key) -> {
      if (key.equals(TEMP_UNIT)) {
        preferenceChangeListener.onTemperatureChanged(0, 1);
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
    return sharedPreferences.getInt(TEMP_UNIT, Temperature.Unit.CELSIUS);
  }

  @Override public void onPreferenceChangeListener(PreferenceChangeListener changeListener) {
    preferenceChangeListener = changeListener;
  }
}

package com.ayush.weatherapp.repository.preferences;

public interface PreferenceRepository {
  void saveTemperatureUnit(int value);

  int getTemperatureUnit();

  void saveLatitude(double lat);

  void saveLongitude(double lng);

  double getLatitude();

  double getLongitude();

  void onPreferenceChangeListener(PreferenceChangeListener changeListener);
}

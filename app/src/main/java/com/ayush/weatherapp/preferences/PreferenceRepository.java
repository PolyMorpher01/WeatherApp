package com.ayush.weatherapp.preferences;

public interface PreferenceRepository {
  void saveTemperatureUnit(int value);

  int getTemperatureUnit();

  void saveCurrentLocationCoordinates(String latlng);

  String getCurrentLocationCoordinates();

  void onPreferenceChangeListener(PreferenceChangeListener changeListener);
}

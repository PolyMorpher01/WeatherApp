package com.ayush.weatherapp.repository.preferences;

public interface PreferenceRepository {
  void saveTemperatureUnit(int value);

  int getTemperatureUnit();

  void saveCurrentLocationCoordinates(String latlng);

  String getCurrentLocationCoordinates();

  void onPreferenceChangeListener(PreferenceChangeListener changeListener);
}

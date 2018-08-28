package com.ayush.weatherapp.preferences;

public interface PreferenceRepository {
  void saveTemperatureUnit(int value);

  int getTemperatureUnit();
}

package com.ayush.weatherapp.preferences;

public interface PreferenceChangeListener {
  void onTemperatureChanged(int oldTemperature, int newTemperature);
}

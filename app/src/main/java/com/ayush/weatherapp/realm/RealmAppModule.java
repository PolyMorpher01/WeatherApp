package com.ayush.weatherapp.realm;

import com.ayush.weatherapp.realm.model.forecast.CurrentForecast;
import com.ayush.weatherapp.realm.model.forecast.DailyData;
import com.ayush.weatherapp.realm.model.forecast.DailyForecast;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.realm.model.forecast.HourlyData;
import com.ayush.weatherapp.realm.model.forecast.HourlyForecast;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {
    Forecast.class, CurrentForecast.class, HourlyForecast.class, HourlyData.class,
    DailyForecast.class, DailyData.class
}) public class RealmAppModule {
}

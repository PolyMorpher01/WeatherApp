package com.ayush.weatherapp.realm;

import com.ayush.weatherapp.realm.model.CurrentForecast;
import com.ayush.weatherapp.realm.model.DailyData;
import com.ayush.weatherapp.realm.model.DailyForecast;
import com.ayush.weatherapp.realm.model.Forecast;
import com.ayush.weatherapp.realm.model.HourlyData;
import com.ayush.weatherapp.realm.model.HourlyForecast;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {
    Forecast.class, CurrentForecast.class, HourlyForecast.class, HourlyData.class,
    DailyForecast.class, DailyData.class
}) public class RealmAppModule {
}

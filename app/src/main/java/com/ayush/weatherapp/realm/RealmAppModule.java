package com.ayush.weatherapp.realm;

import com.ayush.weatherapp.realm.model.forecast.CurrentForecast;
import com.ayush.weatherapp.realm.model.forecast.DailyData;
import com.ayush.weatherapp.realm.model.forecast.DailyForecast;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.realm.model.forecast.HourlyData;
import com.ayush.weatherapp.realm.model.forecast.HourlyForecast;
import com.ayush.weatherapp.realm.model.geocoding.Address;
import com.ayush.weatherapp.realm.model.geocoding.AddressComponents;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.realm.model.geocoding.Geometry;
import com.ayush.weatherapp.realm.model.geocoding.LocationCoordinates;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {
    Forecast.class, CurrentForecast.class, HourlyForecast.class, HourlyData.class,
    DailyForecast.class, DailyData.class, Address.class, AddressComponents.class, GeoLocation.class,
    Geometry.class, LocationCoordinates.class
}) public class RealmAppModule {
}

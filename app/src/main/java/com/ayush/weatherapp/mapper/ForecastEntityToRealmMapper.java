package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.entities.forecast.CurrentForecastEntity;
import com.ayush.weatherapp.entities.forecast.DailyDataEntity;
import com.ayush.weatherapp.entities.forecast.DailyForecastEntity;
import com.ayush.weatherapp.entities.forecast.ForecastEntity;
import com.ayush.weatherapp.entities.forecast.HourlyDataEntity;
import com.ayush.weatherapp.entities.forecast.HourlyForecastEntity;
import com.ayush.weatherapp.realm.model.forecast.CurrentForecast;
import com.ayush.weatherapp.realm.model.forecast.DailyData;
import com.ayush.weatherapp.realm.model.forecast.DailyForecast;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.realm.model.forecast.HourlyData;
import com.ayush.weatherapp.realm.model.forecast.HourlyForecast;
import java.util.ArrayList;
import java.util.List;

public final class ForecastEntityToRealmMapper {
  private ForecastEntityToRealmMapper() {
  }

  public static Forecast transform(ForecastEntity entity) {
    Forecast forecast = new Forecast(entity.getPrimaryKey());
    forecast.setCurrentForecast(transform(entity.getCurrentForecastEntity()));
    forecast.setDailyForecast(transform(entity.getDailyForecastEntity()));
    forecast.setHourlyForecast(transform(entity.getHourlyForecastEntity()));

    return forecast;
  }

  public static CurrentForecast transform(CurrentForecastEntity entity) {
    CurrentForecast currentForecast = new CurrentForecast(entity.getPrimaryKey());
    currentForecast.setTime(entity.getTime());
    currentForecast.setSummary(entity.getSummary());
    currentForecast.setIcon(entity.getIcon());
    currentForecast.setTemperature(entity.getTemperature());

    return currentForecast;
  }

  public static DailyForecast transform(DailyForecastEntity entity) {
    DailyForecast dailyForecast = new DailyForecast(entity.getPrimaryKey());
    dailyForecast.setSummary(entity.getSummary());
    dailyForecast.setIcon(entity.getIcon());
    dailyForecast.setDailyDataList(transformDailyDataList(entity.getDailyDataEntityList()));

    return dailyForecast;
  }

  public static List<DailyData> transformDailyDataList(List<DailyDataEntity> entities) {
    if (entities == null || entities.isEmpty()) {
      return null;
    }

    List<DailyData> dailyDatas = new ArrayList<>(entities.size());
    for (DailyDataEntity entity : entities) {
      dailyDatas.add(transform(entity));
    }
    return dailyDatas;
  }

  public static DailyData transform(DailyDataEntity entity) {
    DailyData dailyData = new DailyData(entity.getPrimaryKey());
    dailyData.setTime(entity.getTime());
    dailyData.setSummary(entity.getSummary());
    dailyData.setIcon(entity.getIcon());
    dailyData.setSunriseTime(entity.getSunriseTime());
    dailyData.setSunsetTime(entity.getSunsetTime());
    dailyData.setTemperatureHigh(entity.getTemperatureHigh());
    dailyData.setTemperatureLow(entity.getTemperatureLow());
    dailyData.setWindSpeed(entity.getWindSpeed());

    return dailyData;
  }

  public static HourlyForecast transform(HourlyForecastEntity entity) {
    HourlyForecast hourlyForecast = new HourlyForecast(entity.getPrimaryKey());
    hourlyForecast.setIcon(entity.getIcon());
    hourlyForecast.setSummary(entity.getSummary());
    hourlyForecast.setHourlyDataList(transformHourlyDataList(entity.getHourlyDataEntityList()));

    return hourlyForecast;
  }

  public static List<HourlyData> transformHourlyDataList(List<HourlyDataEntity> entities) {
    if (entities == null || entities.isEmpty()) {
      return null;
    }

    List<HourlyData> hourlyData = new ArrayList<>(entities.size());

    for (HourlyDataEntity entity : entities) {
      hourlyData.add(transform(entity));
    }

    return hourlyData;
  }

  public static HourlyData transform(HourlyDataEntity entity) {
    HourlyData hourlyData = new HourlyData(entity.getPrimaryKey());
    hourlyData.setTime(entity.getTime());
    hourlyData.setIcon(entity.getIcon());
    hourlyData.setSummary(entity.getSummary());
    hourlyData.setTemperature(entity.getTemperature());

    return hourlyData;
  }
}

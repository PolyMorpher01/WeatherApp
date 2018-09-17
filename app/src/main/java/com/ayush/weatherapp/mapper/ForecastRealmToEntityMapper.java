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

public final class ForecastRealmToEntityMapper {
  private ForecastRealmToEntityMapper() {
  }

  public static ForecastEntity transform(Forecast forecast) {
    ForecastEntity entity = new ForecastEntity();
    entity.setPrimaryKey(forecast.getPrimaryKey());
    entity.setCurrentForecastEntity(transform(forecast.getCurrentForecast()));
    entity.setHourlyForecastEntity(transform(forecast.getHourlyForecast()));
    entity.setDailyForecastEntity(transform(forecast.getDailyForecast()));

    return entity;
  }

  public static CurrentForecastEntity transform(CurrentForecast currentForecast) {
    CurrentForecastEntity entity = new CurrentForecastEntity();
    entity.setPrimaryKey(currentForecast.getPrimaryKey());
    entity.setTime(currentForecast.getTime());
    entity.setSummary(currentForecast.getSummary());
    entity.setIcon(currentForecast.getIcon());
    entity.setTemperature((int) currentForecast.getTemperature());

    return entity;
  }

  public static DailyForecastEntity transform(DailyForecast dailyForecast) {
    DailyForecastEntity entity = new DailyForecastEntity();
    entity.setPrimaryKey(dailyForecast.getPrimaryKey());
    entity.setSummary(dailyForecast.getSummary());
    entity.setIcon(dailyForecast.getIcon());
    entity.setDailyDataEntityList(transformDailyDataList(dailyForecast.getDailyDataList()));

    return entity;
  }

  public static List<DailyDataEntity> transformDailyDataList(List<DailyData> dailyDatas) {
    if (dailyDatas == null || dailyDatas.isEmpty()) {
      return null;
    }

    List<DailyDataEntity> entityList = new ArrayList<>(dailyDatas.size());
    for (DailyData dailyData : dailyDatas) {
      entityList.add(transform(dailyData));
    }
    return entityList;
  }

  public static DailyDataEntity transform(DailyData dailyData) {
    DailyDataEntity entity = new DailyDataEntity();
    entity.setPrimaryKey(dailyData.getPrimaryKey());
    entity.setTime(dailyData.getTime());
    entity.setSummary(dailyData.getSummary());
    entity.setIcon(dailyData.getIcon());
    entity.setSunriseTime(dailyData.getSunriseTime());
    entity.setSunsetTime(dailyData.getSunsetTime());
    entity.setTemperatureHigh((int) dailyData.getTemperatureHigh());
    entity.setTemperatureLow((int) dailyData.getTemperatureLow());
    entity.setWindSpeed(dailyData.getWindSpeed());

    return entity;
  }

  public static HourlyForecastEntity transform(HourlyForecast hourlyForecast) {
    HourlyForecastEntity entity = new HourlyForecastEntity();
    entity.setPrimaryKey(hourlyForecast.getPrimaryKey());
    entity.setIcon(hourlyForecast.getIcon());
    entity.setSummary(hourlyForecast.getSummary());
    entity.setHourlyDataEntityList(transformHourlyDataList(hourlyForecast.getHourlyDataList()));

    return entity;
  }

  public static List<HourlyDataEntity> transformHourlyDataList(List<HourlyData> hourlyDatas) {
    if (hourlyDatas == null || hourlyDatas.isEmpty()) {
      return null;
    }

    List<HourlyDataEntity> entityList = new ArrayList<>(hourlyDatas.size());

    for (HourlyData hourlyData : hourlyDatas) {
      entityList.add(transform(hourlyData));
    }

    return entityList;
  }

  public static HourlyDataEntity transform(HourlyData hourlyData) {
    HourlyDataEntity entity = new HourlyDataEntity();
    entity.setPrimaryKey(hourlyData.getPrimaryKey());
    entity.setTime(hourlyData.getTime());
    entity.setIcon(hourlyData.getIcon());
    entity.setSummary(hourlyData.getSummary());
    entity.setTemperature((int) hourlyData.getTemperature());

    return entity;
  }
}

package com.ayush.weatherapp.entities.forecast;

import io.realm.annotations.PrimaryKey;

public class ForecastEntity {
  @PrimaryKey private long primaryKey;
  private CurrentForecastEntity currentForecastEntity;
  private HourlyForecastEntity hourlyForecastEntity;
  private DailyForecastEntity dailyForecastEntity;

  public long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public CurrentForecastEntity getCurrentForecastEntity() {
    return currentForecastEntity;
  }

  public void setCurrentForecastEntity(CurrentForecastEntity currentForecastEntity) {
    this.currentForecastEntity = currentForecastEntity;
  }

  public HourlyForecastEntity getHourlyForecastEntity() {
    return hourlyForecastEntity;
  }

  public void setHourlyForecastEntity(HourlyForecastEntity hourlyForecastEntity) {
    this.hourlyForecastEntity = hourlyForecastEntity;
  }

  public DailyForecastEntity getDailyForecastEntity() {
    return dailyForecastEntity;
  }

  public void setDailyForecastEntity(DailyForecastEntity dailyForecastEntity) {
    this.dailyForecastEntity = dailyForecastEntity;
  }
}

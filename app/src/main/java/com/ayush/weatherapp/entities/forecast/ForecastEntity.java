package com.ayush.weatherapp.entities.forecast;

public class ForecastEntity {
  private double latitude;
  private double longitude;
  private String timezone;
  private CurrentForecastEntity currentForecastEntity;
  private HourlyForecastEntity hourlyForecastEntity;
  private DailyForecastEntity dailyForecastEntity;

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
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

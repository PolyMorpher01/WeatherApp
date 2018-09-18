package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.forecast.CurrentForecast;
import com.ayush.weatherapp.realm.model.forecast.DailyData;
import com.ayush.weatherapp.realm.model.forecast.DailyForecast;
import com.ayush.weatherapp.realm.model.forecast.Forecast;
import com.ayush.weatherapp.realm.model.forecast.HourlyData;
import com.ayush.weatherapp.realm.model.forecast.HourlyForecast;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.CurrentForecastDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyDataDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecastDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyDataDTO;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecastDTO;
import com.ayush.weatherapp.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;

public final class ForecastDTOtoRealmMapper {

  private ForecastDTOtoRealmMapper() {
  }

  public static Forecast transform(ForecastDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(Forecast.class);
    Forecast forecast = new Forecast(++primaryKey);

    forecast.setCurrentForecast(transform(dto.getCurrentForecastDTO()));
    forecast.setDailyForecast(transform(dto.getDailyForecastDTO()));
    forecast.setHourlyForecast(transform(dto.getHourlyForecastDTO()));
    forecast.setCreatedAt(DateUtils.getCurrentTimeStamp());

    return forecast;
  }

  public static CurrentForecast transform(CurrentForecastDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(CurrentForecast.class);
    CurrentForecast currentForecast = new CurrentForecast(++primaryKey);

    currentForecast.setTime(dto.getTime());
    currentForecast.setSummary(dto.getSummary());
    currentForecast.setIcon(dto.getIcon());
    currentForecast.setTemperature(dto.getTemperature());
    currentForecast.setApparentTemperature(dto.getApparentTemperature());
    currentForecast.setDewPoint(dto.getDewPoint());
    currentForecast.setHumidity(dto.getHumidity());
    currentForecast.setPressure(dto.getHumidity());
    currentForecast.setPressure(dto.getPressure());
    currentForecast.setWindSpeed(dto.getWindSpeed());
    currentForecast.setVisibility(dto.getVisibility());

    return currentForecast;
  }

  public static DailyForecast transform(DailyForecastDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(DailyForecast.class);
    DailyForecast dailyForecast = new DailyForecast(++primaryKey);

    dailyForecast.setSummary(dto.getSummary());
    dailyForecast.setIcon(dto.getIcon());
    dailyForecast.setDailyDataList(transformDailyDataList(dto.getDailyDataDTOList()));

    return dailyForecast;
  }

  public static List<DailyData> transformDailyDataList(List<DailyDataDTO> dailyDataDTOS) {
    if (dailyDataDTOS == null || dailyDataDTOS.isEmpty()) {
      return null;
    }

    List<DailyData> dailyDataList = new ArrayList<>(dailyDataDTOS.size());
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(DailyData.class);
    for (DailyDataDTO dailyDataDTO : dailyDataDTOS) {
      dailyDataList.add(transform(dailyDataDTO, ++primaryKey));
    }
    return dailyDataList;
  }

  public static DailyData transform(DailyDataDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(DailyData.class);
    return transform(dto, ++primaryKey);
  }

  private static DailyData transform(DailyDataDTO dto, long primaryKey) {
    DailyData dailyData = new DailyData(primaryKey);

    dailyData.setTime(dto.getTime());
    dailyData.setSummary(dto.getSummary());
    dailyData.setIcon(dto.getIcon());
    dailyData.setSunriseTime(dto.getSunriseTime());
    dailyData.setSunsetTime(dto.getSunsetTime());
    dailyData.setTemperatureHigh(dto.getTemperatureHigh());
    dailyData.setTemperatureLow(dto.getTemperatureLow());
    dailyData.setApparentTemperatureHigh(dto.getApparentTemperatureHigh());
    dailyData.setApparentTemperatureLow(dto.getApparentTemperatureLow());
    dailyData.setWindSpeed(dto.getWindSpeed());

    return dailyData;
  }

  public static HourlyForecast transform(HourlyForecastDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(HourlyForecast.class);
    HourlyForecast hourlyForecast = new HourlyForecast(++primaryKey);

    hourlyForecast.setSummary(dto.getSummary());
    hourlyForecast.setIcon(dto.getIcon());
    hourlyForecast.setHourlyDataList(transformHourlyDataList(dto.getHourlyDataDTOList()));

    return hourlyForecast;
  }

  public static List<HourlyData> transformHourlyDataList(List<HourlyDataDTO> hourlyDataDTOS) {
    if (hourlyDataDTOS == null || hourlyDataDTOS.isEmpty()) {
      return null;
    }

    List<HourlyData> hourlyDataList = new ArrayList<>(hourlyDataDTOS.size());
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(HourlyData.class);
    for (HourlyDataDTO hourlyDataDTO : hourlyDataDTOS) {
      hourlyDataList.add(transform(hourlyDataDTO, ++primaryKey));
    }
    return hourlyDataList;
  }

  private static HourlyData transform(HourlyDataDTO dto, long primaryKey) {
    HourlyData hourlyData = new HourlyData(primaryKey);

    hourlyData.setIcon(dto.getIcon());
    hourlyData.setTime(dto.getTime());
    hourlyData.setSummary(dto.getSummary());
    hourlyData.setTemperature(dto.getTemperature());
    hourlyData.setApparentTemperature(dto.getApparentTemperature());

    return hourlyData;
  }

  public static HourlyData transform(HourlyDataDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(HourlyData.class);
    return transform(dto, ++primaryKey);
  }
}

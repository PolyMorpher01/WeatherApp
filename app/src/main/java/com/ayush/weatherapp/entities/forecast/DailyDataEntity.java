package com.ayush.weatherapp.entities.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.ayush.weatherapp.utils.MathUtils;

public class DailyDataEntity implements Parcelable {
  public static final Creator<DailyDataEntity> CREATOR = new Creator<DailyDataEntity>() {
    @Override
    public DailyDataEntity createFromParcel(Parcel in) {
      return new DailyDataEntity(in);
    }

    @Override
    public DailyDataEntity[] newArray(int size) {
      return new DailyDataEntity[size];
    }
  };
  private long time;
  private String summary;
  private String icon;
  private int sunriseTime;
  private int sunsetTime;
  private int temperatureHigh;
  private int temperatureLow;
  private double windSpeed;

  protected DailyDataEntity(Parcel in) {
    time = in.readLong();
    summary = in.readString();
    icon = in.readString();
    sunriseTime = in.readInt();
    sunsetTime = in.readInt();
    temperatureHigh = in.readInt();
    temperatureLow = in.readInt();
    windSpeed = in.readDouble();
  }

  public DailyDataEntity() {
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public int getSunriseTime() {
    return sunriseTime;
  }

  public void setSunriseTime(int sunriseTime) {
    this.sunriseTime = sunriseTime;
  }

  public int getSunsetTime() {
    return sunsetTime;
  }

  public void setSunsetTime(int sunsetTime) {
    this.sunsetTime = sunsetTime;
  }

  public int getTemperatureHigh() {
    return temperatureHigh;
  }

  public void setTemperatureHigh(double temperatureHigh) {
    this.temperatureHigh = (int) temperatureHigh;
  }

  public int getTemperatureLow() {
    return temperatureLow;
  }

  public void setTemperatureLow(double temperatureLow) {
    this.temperatureLow = (int) temperatureLow;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  public long getAverageTemperature() {
    return Math.round(MathUtils.getAverage(getTemperatureHigh(), getTemperatureLow()));
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(time);
    dest.writeString(summary);
    dest.writeString(icon);
    dest.writeInt(sunriseTime);
    dest.writeInt(sunsetTime);
    dest.writeDouble(temperatureHigh);
    dest.writeDouble(temperatureLow);
    dest.writeDouble(windSpeed);
  }
}

package com.ayush.weatherapp.retrofit.weatherApi.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class DailyData implements Parcelable {

  @SerializedName("time") private long time;
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("sunriseTime") private int sunriseTime;
  @SerializedName("sunsetTime") private int sunsetTime;
  @SerializedName("temperatureHigh") private double temperatureHigh;
  @SerializedName("temperatureLow") private double temperatureLow;
  @SerializedName("apparentTemperatureHigh") private double apparentTemperatureHigh;
  @SerializedName("apparentTemperatureLow") private double apparentTemperatureLow;
  @SerializedName("windSpeed") private double windSpeed;

  DailyData(Parcel in) {
    time = in.readLong();
    summary = in.readString();
    icon = in.readString();
    sunriseTime = in.readInt();
    sunsetTime = in.readInt();
    temperatureHigh = in.readDouble();
    temperatureLow = in.readDouble();
    apparentTemperatureHigh = in.readDouble();
    apparentTemperatureLow = in.readDouble();
    windSpeed = in.readDouble();
  }

  public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
    @Override public DailyData createFromParcel(Parcel in) {
      return new DailyData(in);
    }

    @Override public DailyData[] newArray(int size) {
      return new DailyData[size];
    }
  };

  public long getTime() {
    return time;
  }

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public int getSunriseTime() {
    return sunriseTime;
  }

  public int getSunsetTime() {
    return sunsetTime;
  }

  public double getTemperatureHigh() {
    return temperatureHigh;
  }

  public double getTemperatureLow() {
    return temperatureLow;
  }

  public double getApparentTemperatureHigh() {
    return apparentTemperatureHigh;
  }

  public double getApparentTemperatureLow() {
    return apparentTemperatureLow;
  }

  public double getWindSpeed() {
    return windSpeed;
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
    dest.writeDouble(apparentTemperatureHigh);
    dest.writeDouble(apparentTemperatureLow);
    dest.writeDouble(windSpeed);
  }

  public void setTime(long time) {
    this.time = time;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setSunriseTime(int sunriseTime) {
    this.sunriseTime = sunriseTime;
  }

  public void setSunsetTime(int sunsetTime) {
    this.sunsetTime = sunsetTime;
  }

  public void setTemperatureHigh(double temperatureHigh) {
    this.temperatureHigh = temperatureHigh;
  }

  public void setTemperatureLow(double temperatureLow) {
    this.temperatureLow = temperatureLow;
  }

  public void setApparentTemperatureHigh(double apparentTemperatureHigh) {
    this.apparentTemperatureHigh = apparentTemperatureHigh;
  }

  public void setApparentTemperatureLow(double apparentTemperatureLow) {
    this.apparentTemperatureLow = apparentTemperatureLow;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }
}
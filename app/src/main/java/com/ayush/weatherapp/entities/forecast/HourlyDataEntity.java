package com.ayush.weatherapp.entities.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.annotations.PrimaryKey;

public class HourlyDataEntity implements Parcelable {
  public static final Creator<HourlyDataEntity> CREATOR = new Creator<HourlyDataEntity>() {
    @Override
    public HourlyDataEntity createFromParcel(Parcel in) {
      return new HourlyDataEntity(in);
    }

    @Override
    public HourlyDataEntity[] newArray(int size) {
      return new HourlyDataEntity[size];
    }
  };
  @PrimaryKey private long primaryKey;
  private long time;
  private String summary;
  private String icon;
  private int temperature;

  protected HourlyDataEntity(Parcel in) {
    primaryKey = in.readLong();
    time = in.readLong();
    summary = in.readString();
    icon = in.readString();
    temperature = in.readInt();
  }

  public HourlyDataEntity() {
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
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

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(primaryKey);
    dest.writeLong(time);
    dest.writeString(summary);
    dest.writeString(icon);
    dest.writeDouble(temperature);
  }
}

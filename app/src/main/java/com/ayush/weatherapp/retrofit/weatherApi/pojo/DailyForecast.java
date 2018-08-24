package com.ayush.weatherapp.retrofit.weatherApi.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyForecast {
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("data") private List<DailyData> dailyDataList = null;

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public List<DailyData> getDailyDataList() {
    return dailyDataList;
  }

  public static class DailyData implements Parcelable {
    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
      @Override
      public DailyData createFromParcel(Parcel in) {
        return new DailyData(in);
      }

      @Override
      public DailyData[] newArray(int size) {
        return new DailyData[size];
      }
    };
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
  }
}

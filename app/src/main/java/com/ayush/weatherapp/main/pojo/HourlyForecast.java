package com.ayush.weatherapp.main.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyForecast {
  @SerializedName("summary") private String summary;
  @SerializedName("icon") private String icon;
  @SerializedName("data") private List<HourlyData> hourlyDataList;

  public String getSummary() {
    return summary;
  }

  public String getIcon() {
    return icon;
  }

  public List<HourlyData> getHourlyDataList() {
    return hourlyDataList;
  }

  public static class HourlyData {
    @SerializedName("time") private int time;
    @SerializedName("summary") private String summary;
    @SerializedName("icon") private String icon;
    @SerializedName("precipIntensity") private float precipIntensity;
    @SerializedName("precipProbability") private float precipProbability;
    @SerializedName("temperature") private float temperature;
    @SerializedName("apparentTemperature") private float apparentTemperature;
    @SerializedName("dewPoint") private float dewPoint;
    @SerializedName("humidity") private float humidity;
    @SerializedName("pressure") private float pressure;
    @SerializedName("windSpeed") private float windSpeed;
    @SerializedName("windBearing") private float windBearing;
    @SerializedName("cloudCover") private float cloudCover;
    @SerializedName("uvIndex") private float uvIndex;
    @SerializedName("visibility") private float visibility;
    @SerializedName("ozone") private float ozone;

    public int getTime() {
      return time;
    }

    public String getSummary() {
      return summary;
    }

    public String getIcon() {
      return icon;
    }

    public float getPrecipIntensity() {
      return precipIntensity;
    }

    public float getPrecipProbability() {
      return precipProbability;
    }

    public float getTemperature() {
      return temperature;
    }

    public float getApparentTemperature() {
      return apparentTemperature;
    }

    public float getDewPoint() {
      return dewPoint;
    }

    public float getHumidity() {
      return humidity;
    }

    public float getPressure() {
      return pressure;
    }

    public float getWindSpeed() {
      return windSpeed;
    }

    public float getWindBearing() {
      return windBearing;
    }

    public float getCloudCover() {
      return cloudCover;
    }

    public float getUvIndex() {
      return uvIndex;
    }

    public float getVisibility() {
      return visibility;
    }

    public float getOzone() {
      return ozone;
    }
  }
}



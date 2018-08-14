package com.ayush.weatherapp.main.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Daily {
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

  public static class DailyData {
    @SerializedName("time") private int time;
    @SerializedName("summary") private String summary;
    @SerializedName("icon") private String icon;
    @SerializedName("sunriseTime") private int sunriseTime;
    @SerializedName("sunsetTime") private int sunsetTime;
    @SerializedName("moonPhase") private float moonPhase;
    @SerializedName("precipIntensity") private float precipIntensity;
    @SerializedName("precipIntensityMax") private float precipIntensityMax;
    @SerializedName("precipIntensityMaxTime") private int precipIntensityMaxTime;
    @SerializedName("precipProbability") private float precipProbability;
    @SerializedName("precipAccumulation") private float precipAccumulation;
    @SerializedName("precipType") private String precipType;
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

    public int getTime() {
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

    public float getMoonPhase() {
      return moonPhase;
    }

    public float getPrecipIntensity() {
      return precipIntensity;
    }

    public float getPrecipIntensityMax() {
      return precipIntensityMax;
    }

    public int getPrecipIntensityMaxTime() {
      return precipIntensityMaxTime;
    }

    public float getPrecipProbability() {
      return precipProbability;
    }

    public float getPrecipAccumulation() {
      return precipAccumulation;
    }

    public String getPrecipType() {
      return precipType;
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
  }
}





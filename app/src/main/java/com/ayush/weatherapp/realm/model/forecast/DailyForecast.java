package com.ayush.weatherapp.realm.model.forecast;

import com.ayush.weatherapp.realm.RealmDeletable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

public class DailyForecast extends RealmObject implements RealmDeletable {

  @PrimaryKey private long primaryKey;
  private String summary;
  private String icon;
  private RealmList<DailyData> dailyDataList;

  public DailyForecast(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public DailyForecast() {
  }

  public long getPrimaryKey() {
    return primaryKey;
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

  public List<DailyData> getDailyDataList() {
    return dailyDataList;
  }

  public void setDailyDataList(List<DailyData> dailyDataList) {
    this.dailyDataList = new RealmList<>();
    this.dailyDataList.addAll(dailyDataList);
  }

  @Override public void removeFromRealm() {
    //todo ask subash
    if (dailyDataList != null) {
      for (int i = dailyDataList.size() - 1; i >= 0; i--) {
        dailyDataList.get(i).removeFromRealm();
      }
    }
    deleteFromRealm();
  }
}

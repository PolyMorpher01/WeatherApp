package com.ayush.weatherapp.realm.model.geocoding;

import com.ayush.weatherapp.realm.RealmDeletable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GeoLocation extends RealmObject implements RealmDeletable {
  @PrimaryKey private long primaryKey;
  private String location;
  private long createdAt;

  public GeoLocation() {
  }

  public GeoLocation(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override public void removeFromRealm() {
    deleteFromRealm();
  }
}

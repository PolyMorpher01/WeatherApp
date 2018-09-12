package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

public class AddressComponents extends RealmObject {
  @PrimaryKey private long primaryKey;
  private String longName;
  private String shortName;
  //private RealmList<String> types;

  public AddressComponents() {
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  //public List<String> getTypes() {
  //  return types;
  //}

  //public void setTypes(List<String> types) {
  //  this.types = new RealmList<>();
  //  this.types.addAll(types);
  //}
}

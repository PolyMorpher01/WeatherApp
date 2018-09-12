package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

public class AddressComponents extends RealmObject {
  @PrimaryKey private long primaryKey;
  private String longName;
  private String shortName;
  //Realmlist<String> not supported in old realm version
  private RealmList<LocationType> types;

  public AddressComponents() {
  }

  public AddressComponents(long primaryKey) {
    this.primaryKey = primaryKey;
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

  public List<String> getTypes() {
    List<String> stringList = new ArrayList<>();
    for (LocationType type : types) {
      stringList.add(getFromLocationType(type));
    }
    return stringList;
  }

  public void setTypes(List<String> types) {
    this.types = new RealmList<>();
    for (String type : types) {
      this.types.add(setToLocationType(type));
    }
  }

  private LocationType setToLocationType(String string) {
    LocationType locationType = new LocationType();
    locationType.setValue(string);
    return locationType;
  }

  private String getFromLocationType(LocationType locationType) {
    return locationType.getValue();
  }
}

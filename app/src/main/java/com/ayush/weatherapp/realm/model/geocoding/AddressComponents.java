package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmList;
import io.realm.RealmObject;
import java.util.ArrayList;
import java.util.List;

public class AddressComponents extends RealmObject {
  private String longName;
  private String shortName;
  //Realmlist<String> not supported in old realm version
  private RealmList<LocationTypes> types;

  public AddressComponents() {
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
    for (LocationTypes type : types) {
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

  private LocationTypes setToLocationType(String string) {
    LocationTypes locationType = new LocationTypes();
    locationType.setValue(string);
    return locationType;
  }

  private String getFromLocationType(LocationTypes locationType) {
    return locationType.getValue();
  }
}

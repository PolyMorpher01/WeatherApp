package com.ayush.weatherapp.realm.model.geocoding;

import com.ayush.weatherapp.realm.RealmString;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

public class AddressComponents extends RealmObject {
  @PrimaryKey private long primaryKey;
  private String longName;
  private String shortName;
  //realmlist<String> not supported in old realm version
  private RealmList<RealmString> types;

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
    for (RealmString type : types) {
      stringList.add(getFromRealmString(type));
    }
    return stringList;
  }

  public void setTypes(List<String> types) {
    this.types = new RealmList<>();
    for (String type : types) {
      this.types.add(setToRealmString(type));
    }
  }

  private RealmString setToRealmString(String string) {
    RealmString realmString = new RealmString();
    realmString.setValue(string);
    return realmString;
  }

  private String getFromRealmString(RealmString realmString) {
    return realmString.getValue();
  }
}

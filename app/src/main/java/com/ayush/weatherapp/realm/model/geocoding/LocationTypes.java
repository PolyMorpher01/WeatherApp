package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;

//Since Realm doesn't support RealmList where E doesn't extend Realm object,
//we wrapped String in a RealmObject.

public class LocationTypes extends RealmObject {
  private String val;

  public String getValue() {
    return val;
  }

  public void setValue(String value) {
    this.val = value;
  }
}

package com.ayush.weatherapp.realm;

import io.realm.RealmObject;

//Since Realm doesn't support RealmList where E doesn't extend Realm object,
//we wrapped String in a RealmObject.

public class RealmString extends RealmObject {
  private String val;

  public String getValue() {
    return val;
  }

  public void setValue(String value) {
    this.val = value;
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

package com.ayush.weatherapp.realm;

public interface RealmDeletable {

  // should only be called from inside a realm transaction
  void removeFromRealm();
}

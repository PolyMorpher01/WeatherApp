package com.ayush.weatherapp.realm;

import android.support.annotation.Nullable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

public final class RealmUtils {

  private static String REALM_NAME = "default.realm";
  private static int CURRENT_VERSION = 1;

  private static RealmConfiguration realmConfiguration;

  private RealmUtils() {

  }

  public static Realm getRealm() {
    return Realm.getInstance(getRealmConfiguration());
  }

  private static RealmConfiguration getRealmConfiguration() {
    if (realmConfiguration == null) {
      realmConfiguration = new RealmConfiguration.Builder().name(REALM_NAME)
          .schemaVersion(CURRENT_VERSION)
          .deleteRealmIfMigrationNeeded()
          .modules(new RealmAppModule())
          .build();
    }
    return realmConfiguration;
  }

  public static <E extends RealmModel> long getMaxIdForPrimaryKey(Class<E> mClass) {
    Realm realm = getRealm();
    Number number = realm.where(mClass).max("primaryKey");
    long maxPrimaryKey = 0;
    if (number != null) {
      maxPrimaryKey = number.longValue();
    }
    realm.close();
    return maxPrimaryKey;
  }

  @Nullable public static <E extends RealmModel> E getRealmModel(Class<E> mClass, long primaryKey) {
    Realm realm = getRealm();
    E realmModel = realm.where(mClass).equalTo("primaryKey", primaryKey).findFirst();
    E realmModelCopy = null;
    if (realmModel != null) {
      realmModelCopy = realm.copyFromRealm(realmModel);
    }
    realm.close();
    return realmModelCopy;
  }

  @Nullable public static <E extends RealmModel> E getRealmModel(Class<E> mClass) {
    Realm realm = getRealm();
    E realmModel = realm.where(mClass).findFirst();
    E realmModelCopy = null;
    if (realmModel != null) {
      realmModelCopy = realm.copyFromRealm(realmModel);
    }
    realm.close();
    return realmModelCopy;
  }
}

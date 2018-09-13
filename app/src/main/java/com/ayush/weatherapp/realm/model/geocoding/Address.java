package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.List;

public class Address extends RealmObject {
  @PrimaryKey private long primaryKey;
  private RealmList<AddressComponents> addressComponents;
  private String formattedAddress;
  private Geometry geometry;

  public Address() {
  }

  public Address(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public long getPrimaryKey() {
    return primaryKey;
  }

  public List<AddressComponents> getAddressComponents() {
    return addressComponents;
  }

  public void setAddressComponents(
      List<AddressComponents> addressComponents) {
    this.addressComponents = new RealmList<>();
    this.addressComponents.addAll(addressComponents);
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public void setFormattedAddress(String formattedAddress) {
    this.formattedAddress = formattedAddress;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }
}

package com.ayush.weatherapp.realm.model.geocoding;

import io.realm.RealmObject;
//TODO
public class LocationTyp extends RealmObject {
  private String streetNumber;
  private String route;
  private String locality;
  private String political;
  private String administrativeArea1;
  private String administrativeArea2;
  private String country;
  private String postalCode;

  public LocationTyp() {
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public String getPolitical() {
    return political;
  }

  public void setPolitical(String political) {
    this.political = political;
  }

  public String getAdministrativeArea1() {
    return administrativeArea1;
  }

  public void setAdministrativeArea1(String administrativeArea1) {
    this.administrativeArea1 = administrativeArea1;
  }

  public String getAdministrativeArea2() {
    return administrativeArea2;
  }

  public void setAdministrativeArea2(String administrativeArea2) {
    this.administrativeArea2 = administrativeArea2;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
}

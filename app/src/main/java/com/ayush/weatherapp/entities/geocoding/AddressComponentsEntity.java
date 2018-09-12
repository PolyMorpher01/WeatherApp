package com.ayush.weatherapp.entities.geocoding;

public class AddressComponentsEntity {
  private String longName;
  private String shortName;
  private String types;

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

  public String getTypes() {
    return types;
  }

  public void setTypes(String types) {
    this.types = types;
  }
}

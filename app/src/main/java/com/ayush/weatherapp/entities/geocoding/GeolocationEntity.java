package com.ayush.weatherapp.entities.geocoding;

import android.text.TextUtils;
import java.util.List;

public class GeolocationEntity {
  private static final String ADDRESS_STREET = "route";
  private static final String ADDRESS_CITY = "locality";
  private static final String ADDRESS_COUNTRY = "country";
  private static final String ADDRESS_ADMINISTRATIVE_AREA = "administrative_area_level_1";

  private AddressEntity address;
  private String status;

  public AddressEntity getAddress() {
    return address;
  }

  public void setAddress(AddressEntity address) {
    this.address = address;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFullAddress() {
    //use only
    List<AddressComponentsEntity> addressComponentsList =
        getAddress().getAddressComponents();

    String primaryAddress = getAddressPrimary(addressComponentsList);
    String secondaryAddress = getAddressSecondary(addressComponentsList);
    String address = "";

    if (!TextUtils.isEmpty(primaryAddress)) {
      address = primaryAddress;
      if (!TextUtils.isEmpty(secondaryAddress)) {
        address += ", " + secondaryAddress;
      }
      return address;
    }

    //case when primary address is empty
    if (!TextUtils.isEmpty(secondaryAddress)) {
      address = secondaryAddress;
      return address;
    }
    return address;
  }

  private String getAddressPrimary(List<AddressComponentsEntity> addressComponentsList) {
    for (AddressComponentsEntity addressComponent : addressComponentsList) {
      if (addressComponent.getTypes().contains(ADDRESS_STREET)) {
        return addressComponent.getLongName();
      }
    }
    return "";
  }

  private String getAddressSecondary(List<AddressComponentsEntity> addressComponentsList) {
    for (AddressComponentsEntity addressComponent : addressComponentsList) {
      if (addressComponent.getTypes().contains(ADDRESS_CITY)) {
        return addressComponent.getLongName();
      }
      if (addressComponent.getTypes().contains(ADDRESS_ADMINISTRATIVE_AREA)) {
        return addressComponent.getLongName();
      }
      if (addressComponent.getTypes().contains(ADDRESS_COUNTRY)) {
        return addressComponent.getLongName();
      }
    }
    return "";
  }
}

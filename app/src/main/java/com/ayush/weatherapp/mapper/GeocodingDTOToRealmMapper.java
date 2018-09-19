package com.ayush.weatherapp.mapper;

import android.text.TextUtils;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.retrofit.geocodingApi.model.AddressDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.model.GeoLocationDTO;
import com.ayush.weatherapp.utils.DateUtils;

public final class GeocodingDTOToRealmMapper {
  private GeocodingDTOToRealmMapper() {
  }

  public static GeoLocation transform(GeoLocationDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(GeoLocation.class);
    GeoLocation geoLocation = new GeoLocation(++primaryKey);

    geoLocation.setLocation(getFullAddress(dto));
    geoLocation.setCreatedAt(DateUtils.getCurrentTimeStamp());
    return geoLocation;
  }

  private static String getFullAddress(GeoLocationDTO dto) {
    String primaryAddress = getPrimaryAddress(dto.getAddressDTO());
    String secondaryAddress = getSecondaryAddress(dto.getAddressDTO());
    String fullAddress = "";

    if (!TextUtils.isEmpty(primaryAddress)) {
      fullAddress = primaryAddress;
      if (!TextUtils.isEmpty(secondaryAddress)) {
        fullAddress = primaryAddress + ", " + secondaryAddress;
      }
      return fullAddress;
    }

    //case when primary Address is empty
    if (!TextUtils.isEmpty(secondaryAddress)) {
      return secondaryAddress;
    }

    //case when both are empty
    if (!TextUtils.isEmpty(dto.getDisplayName())) {
      return dto.getDisplayName();
    }

    return fullAddress;
  }

  private static String getPrimaryAddress(AddressDTO address) {
    return address.getStreet();
  }

  private static String getSecondaryAddress(AddressDTO address) {

    if (!TextUtils.isEmpty(address.getCity())) {
      return address.getCity();
    }

    if (!TextUtils.isEmpty(address.getStateDistrict())) {
      return address.getStateDistrict();
    }

    if (!TextUtils.isEmpty(address.getCountry())) {
      return address.getCountry();
    }

    return "";
  }
}

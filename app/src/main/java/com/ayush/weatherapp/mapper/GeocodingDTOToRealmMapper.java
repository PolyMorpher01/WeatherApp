package com.ayush.weatherapp.mapper;

import android.text.TextUtils;
import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressComponentsDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import java.util.List;

public final class GeocodingDTOToRealmMapper {
  private static final String ADDRESS_STREET = "route";
  private static final String ADDRESS_CITY = "locality";
  private static final String ADDRESS_COUNTRY = "country";
  private static final String ADDRESS_ADMINISTRATIVE_AREA = "administrative_area_level_1";

  private GeocodingDTOToRealmMapper() {
  }

  public static GeoLocation transform(GeoLocationDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(GeoLocation.class);
    GeoLocation geoLocation = new GeoLocation(++primaryKey);

    //we need address detail of only first index
    geoLocation.setLocation(getFullAddress(dto.getAddressDTOS().get(0)));
    geoLocation.setCreatedAt(getCurrentTimeStamp());
    return geoLocation;
  }

  private static String getFullAddress(AddressDTO addressDTOS) {
    List<AddressComponentsDTO> addressComponents = addressDTOS.getAddressComponentDTOS();

    String primaryAddress = getAddressPrimary(addressComponents);
    String secondaryAddress = getAddressSecondary(addressComponents);
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

  private static String getAddressPrimary(List<AddressComponentsDTO> addressComponents) {
    for (AddressComponentsDTO addressComponent : addressComponents) {
      if (addressComponent.getTypes().contains(ADDRESS_STREET)) {
        return addressComponent.getLongName();
      }
    }
    return "";
  }

  private static String getAddressSecondary(List<AddressComponentsDTO> addressComponents) {
    for (AddressComponentsDTO addressComponent : addressComponents) {
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

  private static long getCurrentTimeStamp() {
    return System.currentTimeMillis();
  }
}

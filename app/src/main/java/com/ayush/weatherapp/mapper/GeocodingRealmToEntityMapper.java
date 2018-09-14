package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.entities.geocoding.AddressComponentsEntity;
import com.ayush.weatherapp.entities.geocoding.AddressEntity;
import com.ayush.weatherapp.entities.geocoding.GeolocationEntity;
import com.ayush.weatherapp.realm.model.geocoding.Address;
import com.ayush.weatherapp.realm.model.geocoding.AddressComponents;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import java.util.ArrayList;
import java.util.List;

public final class GeocodingRealmToEntityMapper {
  private GeocodingRealmToEntityMapper() {
  }

  public static GeolocationEntity transform(GeoLocation geolocation) {
    GeolocationEntity entity = new GeolocationEntity();

    entity.setAddress(transform(geolocation.getAddress()));
    entity.setStatus(geolocation.getStatus());

    return entity;
  }

  public static AddressEntity transform(Address address) {
    AddressEntity entity = new AddressEntity();

    entity.setAddressComponents(transformAddressComponentsList(address.getAddressComponents()));
    entity.setFormattedAddress(address.getFormattedAddress());

    return entity;
  }

  public static AddressComponentsEntity transform(AddressComponents addressComponents) {
    AddressComponentsEntity entity = new AddressComponentsEntity();

    entity.setLongName(addressComponents.getLongName());
    entity.setShortName(addressComponents.getShortName());
    entity.setTypes(addressComponents.getTypes());
    return entity;
  }

  public static List<AddressComponentsEntity> transformAddressComponentsList(
      List<AddressComponents> addressComponents) {
    if (addressComponents == null || addressComponents.isEmpty()) {
      return null;
    }
    List<AddressComponentsEntity> entityList = new ArrayList<>(addressComponents.size());

    for (AddressComponents components : addressComponents) {
      entityList.add(transform(components));
    }
    return entityList;
  }
}

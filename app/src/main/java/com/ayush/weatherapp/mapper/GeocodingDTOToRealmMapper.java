package com.ayush.weatherapp.mapper;

import com.ayush.weatherapp.realm.RealmUtils;
import com.ayush.weatherapp.realm.model.geocoding.Address;
import com.ayush.weatherapp.realm.model.geocoding.AddressComponents;
import com.ayush.weatherapp.realm.model.geocoding.GeoLocation;
import com.ayush.weatherapp.realm.model.geocoding.Geometry;
import com.ayush.weatherapp.realm.model.geocoding.LocationCoordinates;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressComponentsDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.AddressDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeoLocationDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.GeometryDTO;
import com.ayush.weatherapp.retrofit.geocodingApi.pojo.LocationCoordinatesDTO;
import java.util.ArrayList;
import java.util.List;

public final class GeocodingDTOToRealmMapper {

  public GeocodingDTOToRealmMapper() {
  }

  public static GeoLocation transform(GeoLocationDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(GeoLocation.class);
    GeoLocation geoLocation = new GeoLocation(++primaryKey);

    geoLocation.setAddress(transformAddressList(dto.getAddressDTOS()));
    geoLocation.setStatus(dto.getStatus());
    return geoLocation;
  }

  public static List<Address> transformAddressList(List<AddressDTO> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      return null;
    }
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(Address.class);
    List<Address> addressList = new ArrayList<>(dtos.size());

    for (AddressDTO dto : dtos) {
      addressList.add(transform(dto, ++primaryKey));
    }
    return addressList;
  }

  private static Address transform(AddressDTO dto, long primaryKey) {
    Address address = new Address(primaryKey);

    address.setAddressComponents(transformAddressComponentsList(dto.getAddressComponentDTOS()));
    address.setFormattedAddress(dto.getFormattedAddress());
    address.setGeometry(transform(dto.getGeometryDTO()));
    return address;
  }

  public static Geometry transform(GeometryDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(Geometry.class);
    Geometry geometry = new Geometry(++primaryKey);

    geometry.setLocationCoordinates(transform(dto.getLocationCoordinatesDTO()));
    geometry.setLocationType(dto.getLocationType());
    return geometry;
  }

  public static LocationCoordinates transform(LocationCoordinatesDTO dto) {
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(LocationCoordinates.class);
    LocationCoordinates locationCoordinates = new LocationCoordinates(++primaryKey);

    locationCoordinates.setLatitude(dto.getLatitude());
    locationCoordinates.setLongitude(dto.getLongitude());
    return locationCoordinates;
  }

  public static List<AddressComponents> transformAddressComponentsList(
      List<AddressComponentsDTO> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      return null;
    }
    long primaryKey = RealmUtils.getMaxIdForPrimaryKey(AddressComponents.class);

    List<AddressComponents> addressComponentsList = new ArrayList<>(dtos.size());
    for (AddressComponentsDTO dto : dtos) {
      addressComponentsList.add(transform(dto, ++primaryKey));
    }

    return addressComponentsList;
  }

  public static AddressComponents transform(AddressComponentsDTO dto, long primaryKey) {
    AddressComponents addressComponents = new AddressComponents(primaryKey);
    //todo for types
    addressComponents.setLongName(dto.getLongName());
    addressComponents.setShortName(dto.getShortName());
    return addressComponents;
  }
}

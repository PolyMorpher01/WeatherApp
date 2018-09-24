package com.ayush.weatherapp.retrofit.geocodingApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeocodingAPIClient {

  //source: https://wiki.openstreetmap.org/wiki/Nominatim#Reverse_Geocoding

  private static final String API_BASE_URL = "https://nominatim.openstreetmap.org/";

  private GeocodingAPIClient() {
  }

  public static Retrofit getClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    return new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build();
  }
}

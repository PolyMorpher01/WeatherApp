package com.ayush.weatherapp.main;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//API client website: https://darksky.net/dev

public class APIClient {
  private static final String API_SECRET_KEY = "b2cfbbc390af11ee7513401d568aef13";

  private static final String API_BASE_URL = "https://api.darksky.net/forecast/" + API_SECRET_KEY + "/";

  static Retrofit getClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    return new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }
}

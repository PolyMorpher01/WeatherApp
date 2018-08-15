package com.ayush.weatherapp.main;

import com.ayush.weatherapp.main.pojo.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

  @GET("{coordinates}")
  Call<Forecast> getForecast(@Path("coordinates") String coordinates);
}

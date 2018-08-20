package com.ayush.weatherapp.home;

import com.ayush.weatherapp.home.pojo.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

  @GET("{coordinates}?exclude=minutely,alerts,flags")
  Call<Forecast> getForecast(@Path("coordinates") String coordinates);
}

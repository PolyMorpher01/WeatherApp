package com.ayush.weatherapp.retrofit.weatherApi;

import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherAPIInterface {

  @GET("{latlng}?exclude=minutely,alerts,flags") Single<ForecastDTO> getForecast(
      @Path("latlng") String latlng);
}

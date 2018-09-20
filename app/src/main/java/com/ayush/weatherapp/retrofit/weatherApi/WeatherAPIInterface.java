package com.ayush.weatherapp.retrofit.weatherApi;

import com.ayush.weatherapp.retrofit.weatherApi.model.ForecastDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherAPIInterface {

  @GET("{lat},{lng}?exclude=minutely,alerts,flags") Observable<ForecastDTO> getForecast(
      @Path("lat") double lat, @Path("lng") double lng);
}

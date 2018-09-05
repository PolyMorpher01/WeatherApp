package com.ayush.weatherapp.retrofit.weatherApi;

import com.ayush.weatherapp.retrofit.weatherApi.pojo.ForecastDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherAPIInterface {

  @GET("{coordinates}?exclude=minutely,alerts,flags") Observable<ForecastDTO> getForecast(
      @Path("coordinates") String coordinates);
}

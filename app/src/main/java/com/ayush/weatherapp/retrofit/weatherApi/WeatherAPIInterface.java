package com.ayush.weatherapp.retrofit.weatherApi;

import com.ayush.weatherapp.retrofit.weatherApi.model.ForecastDTO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherAPIInterface {

  @GET("{latlng}?exclude=minutely,alerts,flags") Observable<ForecastDTO> getForecast(
      @Path("latlng") String latlng);
}

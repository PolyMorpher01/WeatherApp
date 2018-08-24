package com.ayush.weatherapp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.customViews.ForecastCompoundView;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import java.util.List;

public class HourlyForecastFragment extends Fragment {

  @BindView(R.id.ll_forecast_details) LinearLayout llForecastDetails;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  public void setData(List<HourlyForecast.HourlyData> hourlyForeCastList) {
    //remove child views
    if (llForecastDetails.getChildCount() > 0) {
      llForecastDetails.removeAllViews();
    }

    for (HourlyForecast.HourlyData hourlyData : hourlyForeCastList) {
      setView(hourlyData);
    }
  }

  private void setView(HourlyForecast.HourlyData hourlyData) {
    ForecastCompoundView forecastCompoundView =
        (ForecastCompoundView) getLayoutInflater().inflate(R.layout.item_forecast_compound_view,
            llForecastDetails, false);

    forecastCompoundView.setTopText(DateUtils.getTime(hourlyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(hourlyData.getIcon()));
    forecastCompoundView.setBottomText(String.valueOf(Math.round(hourlyData.getTemperature())));

    llForecastDetails.addView(forecastCompoundView, llForecastDetails.getChildCount());
  }
}

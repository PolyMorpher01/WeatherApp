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
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.MathUtils;
import java.util.List;

public class DailyForecastFragment extends Fragment {

  @BindView(R.id.ll_forecast_details) LinearLayout llForecastDetails;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  public void setData(List<DailyForecast.DailyData> dailyForecastList) {
    for (DailyForecast.DailyData dailyData : dailyForecastList) {
      setView(dailyData);
    }
  }

  private void setView(DailyForecast.DailyData dailyData) {
    String averageTemperature = String.valueOf(Math.round(
        MathUtils.getAverage(dailyData.getTemperatureHigh(),
            dailyData.getTemperatureLow())));

    ForecastCompoundView forecastCompoundView =
        (ForecastCompoundView) getLayoutInflater().inflate(R.layout.item_forecast_compound_view,
            llForecastDetails, false);

    forecastCompoundView.setTopText(DateUtils.getDayOfTheWeek(dailyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(dailyData.getIcon()));
    forecastCompoundView.setBottomText(averageTemperature);

    llForecastDetails.addView(forecastCompoundView, llForecastDetails.getChildCount());
  }
}

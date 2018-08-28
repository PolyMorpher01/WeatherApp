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
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.customViews.ForecastCompoundView;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.preferences.PreferenceRepository;
import com.ayush.weatherapp.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.HourlyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.UnitConversionUtils;
import java.util.ArrayList;
import java.util.List;

public class HourlyForecastFragment extends Fragment {

  public static final String EXTRA_HOURLY_FORECAST = "HourlyForecastList";
  @BindView(R.id.ll_forecast_details) LinearLayout llForecastDetails;
  private PreferenceRepository preferenceRepository;

  public static HourlyForecastFragment getInstance(List<HourlyForecast.HourlyData> hourlyDataList) {
    HourlyForecastFragment hourlyForecastFragment = new HourlyForecastFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(EXTRA_HOURLY_FORECAST,
        (ArrayList<HourlyForecast.HourlyData>) hourlyDataList);
    hourlyForecastFragment.setArguments(bundle);
    return hourlyForecastFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    preferenceRepository = PreferenceRepositoryImpl.get();
    View view = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, view);
    setData(getArguments().getParcelableArrayList(EXTRA_HOURLY_FORECAST));
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

    double hourlyTemperature = hourlyData.getTemperature();

    if (preferenceRepository.getTemperatureUnit() == Temperature.Unit.CELSIUS) {
      hourlyTemperature = UnitConversionUtils.fahrenheitToCelsius(hourlyTemperature);
    }

    forecastCompoundView.setTopText(DateUtils.getTime(hourlyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(hourlyData.getIcon()));
    forecastCompoundView.setBottomText(String.valueOf(Math.round(hourlyTemperature)));

    llForecastDetails.addView(forecastCompoundView, llForecastDetails.getChildCount());
  }
}

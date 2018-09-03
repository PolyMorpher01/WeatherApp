package com.ayush.weatherapp.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.TemperatureUnit;
import com.ayush.weatherapp.customViews.ForecastCompoundView;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.repository.preferences.PreferenceRepository;
import com.ayush.weatherapp.repository.preferences.PreferenceRepositoryImpl;
import com.ayush.weatherapp.retrofit.weatherApi.pojo.DailyForecast;
import com.ayush.weatherapp.utils.DateUtils;
import com.ayush.weatherapp.utils.MathUtils;
import com.ayush.weatherapp.utils.UnitConversionUtils;
import java.util.ArrayList;
import java.util.List;

public class DailyForecastFragment extends Fragment {
  public static final String EXTRA_DAILY_FORECAST = "DailyForecastList";

  @BindView(R.id.ll_forecast_details) LinearLayout llForecastDetails;

  private PreferenceRepository preferenceRepository;

  public static DailyForecastFragment getInstance(List<DailyForecast.DailyData> dailyDataList) {
    DailyForecastFragment dailyForecastFragment = new DailyForecastFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(EXTRA_DAILY_FORECAST,
        (ArrayList<? extends Parcelable>) dailyDataList);
    dailyForecastFragment.setArguments(bundle);
    return dailyForecastFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    preferenceRepository = PreferenceRepositoryImpl.get();
    View view = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, view);
    setData(getArguments().getParcelableArrayList(EXTRA_DAILY_FORECAST));
    return view;
  }

  public void setData(List<DailyForecast.DailyData> dailyForecastList) {
    //remove child views
    if (llForecastDetails.getChildCount() > 0) {
      llForecastDetails.removeAllViews();
    }

    for (DailyForecast.DailyData dailyData : dailyForecastList) {
      setView(dailyData);
    }
  }

  private void setView(DailyForecast.DailyData dailyData) {
    double averageTemperature = Math.round(
        MathUtils.getAverage(dailyData.getTemperatureHigh(), dailyData.getTemperatureLow()));

    if (preferenceRepository.getTemperatureUnit() == TemperatureUnit.CELSIUS) {
      averageTemperature = UnitConversionUtils.fahrenheitToCelsius(averageTemperature);
    }

    ForecastCompoundView forecastCompoundView =
        (ForecastCompoundView) getLayoutInflater().inflate(R.layout.item_forecast_compound_view,
            llForecastDetails, false);

    forecastCompoundView.setTopText(DateUtils.getDayOfTheWeek(dailyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(dailyData.getIcon()));
    forecastCompoundView.setBottomText(String.valueOf(Math.round(averageTemperature)));

    llForecastDetails.addView(forecastCompoundView, llForecastDetails.getChildCount());
  }
}

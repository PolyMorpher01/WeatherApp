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
import com.ayush.weatherapp.entities.HourlyDataEntity;
import com.ayush.weatherapp.mapper.WeatherImageMapper;
import com.ayush.weatherapp.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;

public class HourlyForecastFragment extends Fragment {

  public static final String EXTRA_HOURLY_FORECAST = "HourlyForecastList";
  @BindView(R.id.ll_forecast_details) LinearLayout llForecastDetails;

  public static HourlyForecastFragment getInstance(List<HourlyDataEntity> hourlyDataList) {
    HourlyForecastFragment hourlyForecastFragment = new HourlyForecastFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(EXTRA_HOURLY_FORECAST,
        (ArrayList<HourlyDataEntity>) hourlyDataList);
    hourlyForecastFragment.setArguments(bundle);
    return hourlyForecastFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, view);
    if (getArguments() != null) {
      setData(getArguments().getParcelableArrayList(EXTRA_HOURLY_FORECAST));
    }
    return view;
  }

  public void setData(List<HourlyDataEntity> hourlyForeCastList) {
    llForecastDetails.removeAllViews();

    for (HourlyDataEntity hourlyData : hourlyForeCastList) {
      setView(hourlyData);
    }
  }

  private void setView(HourlyDataEntity hourlyData) {
    ForecastCompoundView forecastCompoundView =
        (ForecastCompoundView) getLayoutInflater().inflate(R.layout.item_forecast_compound_view,
            llForecastDetails, false);

    forecastCompoundView.setTopText(DateUtils.getTime(hourlyData.getTime()));
    forecastCompoundView.setMidImage(
        WeatherImageMapper.getSmallImageResource(hourlyData.getIcon()));
    forecastCompoundView.setBottomText(String.valueOf(hourlyData.getTemperature()));

    llForecastDetails.addView(forecastCompoundView, llForecastDetails.getChildCount());
  }
}

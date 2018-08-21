package com.ayush.weatherapp.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ayush.weatherapp.R;

public class ForecastCompoundView extends LinearLayout {

  private TextView tvTop;
  private ImageView ivMid;
  private TextView tvBottom;

  public ForecastCompoundView(Context context) {
    this(context, null);
  }

  public ForecastCompoundView(Context context,
      @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public ForecastCompoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  public ForecastCompoundView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(@Nullable AttributeSet attributeSet) {
    inflate(getContext(), R.layout.forecast_compound_view, this);

    tvTop = findViewById(R.id.tv_day);
    ivMid = findViewById(R.id.iv_weather_icon);
    tvBottom = findViewById(R.id.tv_temp);

    TypedArray typedArray =
        getContext().obtainStyledAttributes(attributeSet, R.styleable.ForecastCompoundView);

    setValues(typedArray);

    typedArray.recycle();
  }

  private void setValues(TypedArray typedArray) {
    tvTop.setText(typedArray.getString(R.styleable.ForecastCompoundView_day));
    ivMid.setImageDrawable(
        typedArray.getDrawable(R.styleable.ForecastCompoundView_weather_icon));
    tvBottom.setText(typedArray.getString(R.styleable.ForecastCompoundView_temperature));
  }

  public TextView getTvTop() {
    return tvTop;
  }

  public void setTvTop(String text) {
    tvTop.setText(text);
  }

  public ImageView getIvMid() {
    return ivMid;
  }

  public void setIvMid(int imageResource) {
    ivMid.setImageResource(imageResource);
  }

  public TextView getTvBottom() {
    return tvBottom;
  }

  public void setTvBottom(String text) {
    tvBottom.setText(text);
  }
}

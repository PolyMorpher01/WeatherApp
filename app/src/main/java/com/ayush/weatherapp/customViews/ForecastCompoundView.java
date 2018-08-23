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

  private static final int TYPE_CELSIUS = 0;
  private static final int TYPE_FAHRENHEIT = 1;

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
    this.setTopText(typedArray.getString(R.styleable.ForecastCompoundView_day));
    this.setMidImage(typedArray.getResourceId(R.styleable.ForecastCompoundView_weather_icon,
        R.drawable.img_no_connection));
    this.setBottomText(typedArray.getString(R.styleable.ForecastCompoundView_temperature));
  }

  public void setTopText(String text) {
    tvTop.setText(text);
  }

  public void setMidImage(int imageResource) {
    ivMid.setImageResource(imageResource);
  }

  public void setBottomText(String text) {
    tvBottom.setText(text);
  }
}

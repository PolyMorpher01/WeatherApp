package com.ayush.weatherapp.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.TemperatureConstant;

public class TemperatureTextView extends AppCompatTextView {

  private int temperatureType;
  private static final float PROPORTION_HALF = 0.5f;

  public TemperatureTextView(Context context) {
    this(context, null);
  }

  public TemperatureTextView(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public TemperatureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  public void init(AttributeSet attributeSet) {
    TypedArray typedArray =
        getContext().obtainStyledAttributes(attributeSet, R.styleable.TemperatureTextView);
    setValue(typedArray);
    typedArray.recycle();
  }

  private void setValue(TypedArray typedArray) {
    setTemperatureType(typedArray.getInt(R.styleable.TemperatureTextView_temp_unit, 0));
  }

  private int getTemperatureType() {
    return temperatureType;
  }

  public void setTemperatureType(@TemperatureConstant.Temperature int temperatureType) {
    this.temperatureType = temperatureType;
  }

  @Override public void setText(CharSequence text, BufferType type) {
    if (getTemperatureType() == TemperatureConstant.Temperature.FAHRENHEIT) {
      text = getResources().getString(R.string.format_temperature_fahrenheit, text);
    } else {
      text = getResources().getString(R.string.format_temperature_celsius, text);
    }
    Spannable spannableText =
        getSpannableTextSize((String) text, PROPORTION_HALF, text.length() - 2, text.length());

    super.setText(spannableText, type);
  }

  @NonNull
  private Spannable getSpannableTextSize(String currentTemp, float proportion, int start, int end) {
    Spannable spannableCurrentTemp = new SpannableString(currentTemp);
    spannableCurrentTemp.setSpan(new RelativeSizeSpan(proportion), start, end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableCurrentTemp;
  }
}

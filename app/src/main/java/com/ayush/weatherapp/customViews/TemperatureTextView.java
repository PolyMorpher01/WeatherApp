package com.ayush.weatherapp.customViews;

import android.content.Context;
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

  private static final char[] temperatureType = { 'F', 'C' };

  public TemperatureTextView(Context context) {
    super(context);
  }

  public TemperatureTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TemperatureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setTemperature(long temperature, @TemperatureConstant.Temperature int tempType) {
    String tempString;

    if (tempType == TemperatureConstant.FAHRENHEIT) {
      tempString = getResources().getString(R.string.format_temperature_fahrenheit, temperature);
    } else {
      tempString = getResources().getString(R.string.format_temperature_celsius, temperature);
    }

    //Decrease only the size of the temperature unit by half
    Spannable spannable =
        getSpannableTextSize(tempString, 0.5f, tempString.length() - 2, tempString.length());
    setText(spannable);
  }

  @NonNull
  private Spannable getSpannableTextSize(String currentTemp, float proportion, int start, int end) {
    Spannable spannableCurrentTemp = new SpannableString(currentTemp);
    spannableCurrentTemp.setSpan(new RelativeSizeSpan(proportion), start, end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableCurrentTemp;
  }
}
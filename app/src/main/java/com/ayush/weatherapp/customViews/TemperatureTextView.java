package com.ayush.weatherapp.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.constants.Temperature;
import com.ayush.weatherapp.constants.TemperatureUnit;

public class TemperatureTextView extends AppCompatTextView {

  private static final float PROPORTION_HALF = 0.5f;
  private int temperatureType;
  private float proportion;

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
    setTemperatureType(typedArray.getInt(R.styleable.TemperatureTextView_temp_unit,
        TemperatureUnit.FAHRENHEIT));
    setProportion(typedArray.getFloat(R.styleable.TemperatureTextView_proportion, PROPORTION_HALF));
  }

  public void setTemperatureType(@Temperature int temperatureType) {
    this.temperatureType = temperatureType;
    requestLayout();
  }

  public void setProportion(float proportion) {
    this.proportion = proportion;
    requestLayout();
  }

  @Override public void setText(CharSequence text, BufferType type) {
    if (TextUtils.isEmpty(text)) {
      super.setText("", type);
      return;
    }

    Spannable spannableText = getSpannableTextSize((String) text, text.length() - 2, text.length());

    super.setText(spannableText, type);
  }

  @NonNull
  private Spannable getSpannableTextSize(String currentTemp, int start, int end) {
    Spannable spannableCurrentTemp = new SpannableString(currentTemp);
    spannableCurrentTemp.setSpan(new RelativeSizeSpan(proportion), start, end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    spannableCurrentTemp.setSpan(new SuperscriptSpan(), start, end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    return spannableCurrentTemp;
  }
}

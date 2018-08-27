package com.ayush.weatherapp.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ayush.weatherapp.R;

public class ForecastDetailCompoundView extends RelativeLayout {

  private ImageView ivLeftIcon;
  private TextView tvTop;
  private TextView tvBottom;

  public ForecastDetailCompoundView(Context context) {
    this(context, null);
  }

  public ForecastDetailCompoundView(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public ForecastDetailCompoundView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  public ForecastDetailCompoundView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(@Nullable AttributeSet attributeSet) {
    inflate(getContext(), R.layout.forecast_detail_compound_view, this);

    ivLeftIcon = findViewById(R.id.iv_detail_icon);
    tvTop = findViewById(R.id.tv_top);
    tvBottom = findViewById(R.id.tv_bottom);

    TypedArray typedArray =
        getContext().obtainStyledAttributes(attributeSet, R.styleable.ForecastDetailCompoundView);

    setValues(typedArray);

    typedArray.recycle();
  }

  private void setValues(TypedArray typedArray) {
    setLeftImage(typedArray.getResourceId(R.styleable.ForecastDetailCompoundView_detail_icon,
        R.drawable.img_small_cloudy));
    setTopText(typedArray.getString(R.styleable.ForecastDetailCompoundView_txt_top));
    setBottomText(typedArray.getString(R.styleable.ForecastDetailCompoundView_txt_bottom));
  }

  public void setLeftImage(int imgResource) {
    ivLeftIcon.setImageResource(imgResource);
  }

  public void setTopText(String text) {
    tvTop.setText(text);
  }

  public void setBottomText(String text) {
    tvBottom.setText(text);
  }
}

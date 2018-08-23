package com.ayush.weatherapp.customViews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

//Reference: https://stackoverflow.com/questions/8394681/android-i-am-unable-to-have-viewpager-wrap-content

//Custom ViewPager that defaults to wrap content in height rather than match parent
public class WrapContentHeightViewPager extends ViewPager {
  public WrapContentHeightViewPager(Context context) {
    super(context);
  }

  public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    int height = 0;
    for (int i = 0; i < getChildCount(); i++) {
      View child = getChildAt(i);
      child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
      int h = child.getMeasuredHeight();
      if (h > height) height = h;
    }

    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}

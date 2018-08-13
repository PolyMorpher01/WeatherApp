package com.ayush.weatherapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.ayush.weatherapp.R;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }
}

package com.ayush.weatherapp.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.ayush.weatherapp.BaseActivity;
import com.ayush.weatherapp.R;
import com.ayush.weatherapp.main.pojo.Currently;
import com.ayush.weatherapp.main.pojo.Forecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

  final ProgressDialog progress = new ProgressDialog(this);

  @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.txt_main) TextView textView;

  APIInterface apiInterface;

  @Override protected int getContextView() {
    return R.layout.navigation_drawer;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);

    setActionBar();

    setNavigationView();

    apiInterface = APIClient.getClient().create(APIInterface.class);
    apiResponse();
  }

  private void apiResponse() {

    showProgressDialog();

    final double TEST_LATITUDE = 37.8267;
    final double TEST_LONGITUDE = -122.4233;
    String requestString = TEST_LATITUDE + "," + TEST_LONGITUDE;

    Call<Forecast> forecastCall = apiInterface.getForecast(requestString);

    forecastCall.enqueue(new Callback<Forecast>() {
      @Override
      public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
        Forecast forecast = response.body();

        Currently currently = forecast.getCurrently();

        textView.setText(currently.getSummary());

        closeProgressDialog();
      }

      @Override public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
        call.cancel();
        closeProgressDialog();
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setActionBar() {
    setSupportActionBar(toolbar);

    ActionBar actionbar = getSupportActionBar();

    if (actionbar != null) {
      actionbar.setDisplayShowTitleEnabled(false);
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }
  }

  private void setNavigationView() {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {

          @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
            //TODO
            Toast.makeText(MainActivity.this, menuItem.toString(), Toast.LENGTH_SHORT).show();

            drawerLayout.closeDrawers();

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            return true;
          }
        });
  }

  private void closeProgressDialog() {
    progress.dismiss();
  }

  private void showProgressDialog() {
    progress.setMessage("Loading");
    progress.show();
  }
}

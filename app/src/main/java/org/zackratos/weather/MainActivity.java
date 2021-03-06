package org.zackratos.weather;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.zackratos.weather.hewind.Now;
import org.zackratos.weather.service.WeatherService;
import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.weather.WeatherFragment;
import org.zackratos.weather.weatherlist.WeatherListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements
        WeatherFragment.Callback, WeatherListFragment.Callback {


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }



    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.main_name_view)
    TextView nameView;

    @BindView(R.id.place_container)
    FrameLayout weatherContainer;

    @BindView(R.id.main_coordinator)
    CoordinatorLayout coordinator;

    @BindView(R.id.main_drawer_view)
    FrameLayout drawerView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    @BindView(R.id.main_background)
    ImageView backgroundView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);


        setColorBarForDrawer(ContextCompat.getColor(this, R.color.main));




        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

        setSupportActionBar(toolbar);

//        DrawerLayout drawer = ButterKnife.findById(this, R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_drawer_view, WeatherListFragment.newInstance())
                .commit();

    }








    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: ");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            
            return true;
        } else if (id == R.id.action_exit)  {
            Log.d(TAG, "onOptionsItemSelected: exit");
            stopService(WeatherService.newIntent(this));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setColorBarForDrawer(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
/*            if (navigationBarExist()) {
                option = option | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }*/
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(color);
            decorView.addView(createStatusBarView(color), 0);
/*            if (navigationBarExist()) {
                decorView.addView(createNavBarView(color));
                ViewGroup.MarginLayoutParams drawerParams = (ViewGroup.MarginLayoutParams)
                        drawerView.getLayoutParams();
                drawerParams.bottomMargin += getNavigationHeight();
                drawerView.setLayoutParams(drawerParams);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)
                        weatherContainer.getLayoutParams();
                params.bottomMargin += getNavigationHeight();
                weatherContainer.setLayoutParams(params);
            }*/
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(createStatusBarView(color), 0);
            if (navigationBarExist()) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                decorView.addView(createNavBarView(color), 1);

                ViewGroup.MarginLayoutParams drawerParams = (ViewGroup.MarginLayoutParams)
                        drawerView.getLayoutParams();
                drawerParams.bottomMargin += getNavigationHeight();
                drawerView.setLayoutParams(drawerParams);
            }
        }
    }




/*    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/



/*    @Override
    public void switchWeather(int id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.place_container, WeatherFragment.newInstance(id))
                .commit();
    }*/


    @Override
    public void onWeatherChecked(Weather weather) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.place_container, WeatherFragment.newInstance(weather.getWeatherId()))
                .commit();
        nameView.setText(weather.getCountyName());

    }


    @Override
    public void closeDrawer(boolean animate) {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START, animate);
        }
    }





    @Override
    public void setName(String name) {
        nameView.setText(name);
    }

    @Override
    public void setNowInfo(Now now) {
        if (now == null) {
            collapsingToolbar.setTitle(" ");
            Glide.with(this).load("").into(backgroundView);
            return;
        }
        collapsingToolbar.setTitle(now.getCond().getTxt() + " " + now.getTmp() + "°");
        Glide.with(this).load(bgUrl(now.getCond().getCode())).into(backgroundView);
    }



    private static final String BG_URL = "https://raw.githubusercontent.com/Zackratos/MaterialWeather/master/background/";




    private String bgUrl(String code) {
        String newCode;
        switch (code) {
            case "102":
            case "103":
                newCode = "101";
                break;

            case "202":
            case "203":
            case "204":
            case "205":
            case "206":
            case "207":
                newCode = "200";
                break;

            case "208":
            case "209":
            case "210":
            case "211":
            case "213":
                newCode = "212";
                break;

            case "301":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
                newCode = "300";
                break;

            case "303":
            case "304":
                newCode = "302";
                break;

            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "407":
                newCode = "400";
                break;

            case "501":
            case "502":
                newCode = "500";
                break;

            case "504":
            case "507":
            case "508":
                newCode = "503";
                break;

            default:
                newCode = code;
                break;
        }
        String url = String.format("%s%s.jpg", BG_URL, newCode);
        Log.d(TAG, "bgUrl: " + url);
        return url;
    }
}

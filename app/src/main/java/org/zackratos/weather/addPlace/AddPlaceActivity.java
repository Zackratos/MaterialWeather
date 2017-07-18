package org.zackratos.weather.addPlace;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.zackratos.ultimatebar.UltimateBar;
import org.zackratos.weather.BaseActivity;
import org.zackratos.weather.weather.HeWindApi;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.R;
import org.zackratos.weather.SPUtils;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.hewind.srarch.HeSearch;
import org.zackratos.weather.hewind.srarch.SearchBasic;
import org.zackratos.weather.hewind.srarch.SearchHeWeather5;
import org.zackratos.xmaplocation.LocateListener;
import org.zackratos.xmaplocation.Location;
import org.zackratos.xmaplocation.XMapLocation;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.zackratos.weather.Constants.AddPlace;

public class AddPlaceActivity extends BaseActivity implements PlaceCallback {

    public static Intent newIntent(Context context) {
        return new Intent(context, AddPlaceActivity.class);
    }


    @BindView(R.id.add_place_locate)
    FloatingActionButton locateButton;


    private ProgressDialog dialog;

    @OnClick(R.id.add_place_locate)
    void onLocateClick() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            initDialog();
                            dialog.show();
                            locate();
                        }
                    }
                });

    }


    private void initDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.add_place_locate));
            dialog.setCanceledOnTouchOutside(false);
        }
    }



    private void locate() {

        XMapLocation.newBuilder(this)
                .build()
                .locate(new LocateListener() {
                    @Override
                    public void onLocated(Location location) {
                        if (!location.isSuccess()) {
                            dialog.dismiss();
                            SingleToast.getInstance(AddPlaceActivity.this)
                                    .show(location.getErrorInfo());
                            return;
                        }

                        locateSuccess(location);
                    }
                });
    }




    private Disposable disposable;



    private static final String WEATHER_SID = "weather_sid";


    private void locateSuccess(Location location) {
        HttpUtils.getHeWindRetrofit()
                .create(HeWindApi.class)
                .rxSearch(HttpUtils.getHeWindMap(location.getLongitude() + "," + location.getLatitude()))
                .subscribeOn(Schedulers.io())
                .compose(this.<HeSearch>bindToLifecycle())
                .flatMap(new Function<HeSearch, ObservableSource<SearchBasic>>() {
                    @Override
                    public ObservableSource<SearchBasic> apply(@NonNull HeSearch heSearch) throws Exception {
                        SearchHeWeather5 heWeather5 = heSearch.getHeWeather5().get(0);
                        String status = heWeather5.getStatus();
                        if (status.equals("ok")) {
                            return Observable.just(heWeather5.getBasic());
                        }
                        return Observable.error(new Throwable(status));
                    }
                })
                .doOnNext(new Consumer<SearchBasic>() {
                    @Override
                    public void accept(@NonNull SearchBasic searchBasic) throws Exception {
//                        ContentValues values = new ContentValues();
//                        values.put("checked", false);
//                        DataSupport.updateAll(Weather.class, values, "weatherid != ?", searchBasic.getId());
                        int index = SPUtils.getWeatherIndex(AddPlaceActivity.this);
                        Weather weather = new Weather.Builder()
                                .weatherId(searchBasic.getId())
                                .countyName(searchBasic.getCity())
                                .index(index)
                                .build();
                        weather.saveOrUpdate("weatherid = ?", searchBasic.getId());
                        SPUtils.putWeatherId(AddPlaceActivity.this, searchBasic.getId());
                        SPUtils.putWeatherIndex(AddPlaceActivity.this, ++index);
                    }
                })
                .subscribe(new Observer<SearchBasic>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        AddPlaceActivity.this.disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SearchBasic searchBasic) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        SingleToast.getInstance(AddPlaceActivity.this)
                                .show(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                        setResult(AddPlace.LOCATE_RESULT);
                        finish();
                    }
                });


    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);


        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.main));

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && navigationBarExist()) {
            FrameLayout placeContainer = ButterKnife.findById(this, R.id.place_container);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) placeContainer.getLayoutParams();
            params.bottomMargin = getNavigationHeight();
            placeContainer.setLayoutParams(params);
        }



        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.place_container, ProvinceFragment.newInstance())
                .commit();

    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_place, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private static final String COUNTY_ID = "county_id";

    public static int getCountyId(Intent intent) {

        return intent.getIntExtra(COUNTY_ID, 0);
    }



    public static int getWeatherSid(Intent intent) {
        return intent.getIntExtra(WEATHER_SID, 1);
    }



    public static String getWeatherId(Intent intent) {
        return intent.getStringExtra(AddPlace.WEATHER_ID);
    }

    public static SearchBasic getBasic(Intent intent) {
        return intent.getParcelableExtra(AddPlace.EXTRA_BASIC);
    }









    @Override
    public void replaceFragment(PlaceFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.place_container, fragment)
                .commit();
    }



    @Override
    public void initLocateButton(RecyclerView recyclerView) {
        locateButton.attachToRecyclerView(recyclerView);
    }




    @Override
    public void addCounty(County county) {

        Observable.just(county)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<County>() {
                    @Override
                    public void accept(@NonNull County county) throws Exception {
                        int index = SPUtils.getWeatherIndex(AddPlaceActivity.this);
                        Weather weather = new Weather.Builder()
                                .weatherId(county.getWeatherId())
                                .countyName(county.getName())
                                .index(index)
                                .build();
                        weather.saveOrUpdate("weatherid = ?", county.getWeatherId());
                        SPUtils.putWeatherId(AddPlaceActivity.this, county.getWeatherId());
                        SPUtils.putWeatherIndex(AddPlaceActivity.this, ++index);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<County>() {
                    @Override
                    public void accept(@NonNull County county) throws Exception {
                        setResult(AddPlace.SELECT_RESULT);
                        finish();
                    }
                });


/*        new Thread(new Runnable() {
            @Override
            public void run() {
                Weather weather = new Weather.Builder()
                        .weatherId(county.getWeatherId())
                        .countyName(county.getName())
                        .build();
                weather.saveOrUpdate("weatherid = ?", county.getWeatherId());
                SPUtils.putWeatherId(AddPlaceActivity.this, county.getWeatherId());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setResult(AddPlace.SELECT_RESULT);
                        finish();
                    }
                });
            }
        }).start();*/





/*        new Thread(new Runnable() {
            @Override
            public void run() {

                ContentValues values = new ContentValues();
                values.put("checked", false);
                DataSupport.updateAll(Weather.class, values, "weatherid != ?", county.getWeatherId());

                Weather weather = new Weather.Builder()
                        .checked(true)
                        .countyName(county.getName())
                        .weatherId(county.getWeatherId())
                        .build();
                weather.saveOrUpdate("weatherid = ?", county.getWeatherId());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        setResult(AddPlace.SELECT_RESULT);
                        finish();
                    }
                });
            }
        }).start();*/


    }

}

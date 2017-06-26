package org.zackratos.weather.addPlace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.melnykov.fab.FloatingActionButton;

import org.zackratos.ultimatebar.UltimateBar;
import org.zackratos.weather.BaseActivity;
import org.zackratos.weather.HeWindApi;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.addPlace.province.ProvinceFragment;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.zackratos.weather.Constants.AddPlace;

public class AddPlaceActivity extends BaseActivity implements PlaceCallback {

    public static Intent newIntent(Context context) {
        return new Intent(context, AddPlaceActivity.class);
    }


    @BindView(R.id.add_place_locate)
    FloatingActionButton locateButton;

    @OnClick(R.id.add_place_locate)
    void onLocateClick() {
        XMapLocation.newBuilder(this)
                .build()
                .locate(new LocateListener() {
                    @Override
                    public void onLocated(Location location) {
                        if (!location.isSuccess()) {
                            SingleToast.getInstance(AddPlaceActivity.this)
                                    .show(location.getErrorInfo());
                            return;
                        }
                        locateSuccess(location);
                    }
                });
    }


    private Disposable disposable;


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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBasic>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        AddPlaceActivity.this.disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SearchBasic searchBasic) {
                        Intent intent = new Intent();
                        intent.putExtra(AddPlace.EXTRA_BASIC, searchBasic);
                        setResult(AddPlace.LOCATE_RESULT, intent);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        SingleToast.getInstance(AddPlaceActivity.this)
                                .show(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

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

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.weather_container, ProvinceFragment.newInstance())
                .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
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



    public static int getCountyId(Intent intent) {

        return intent.getIntExtra(AddPlace.COUNTY_ID, 0);
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
                .replace(R.id.weather_container, fragment)
                .commit();
    }



    @Override
    public void initLocateButton(RecyclerView recyclerView) {
        locateButton.attachToRecyclerView(recyclerView);
    }


    @Override
    public void addCounty(int countyId) {
        Intent intent = new Intent();
        intent.putExtra(AddPlace.COUNTY_ID, countyId);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}

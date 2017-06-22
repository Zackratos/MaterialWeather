package org.zackratos.weather.addPlace.province;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import org.litepal.crud.DataSupport;
import org.zackratos.weather.Constants;
import org.zackratos.weather.PlaceApi;
import org.zackratos.weather.Province;
import org.zackratos.weather.addPlace.AddPlaceActivity;
import org.zackratos.weather.addPlace.PlaceFragment;
import org.zackratos.weather.addPlace.city.CityFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/17.
 */

public class ProvinceFragment extends PlaceFragment {


    public static ProvinceFragment newInstance() {
        return new ProvinceFragment();
    }





    private ProvincePresenter presenter;
    private List<Province> provinces;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ProvincePresenter(null);
        Log.d(TAG, "onCreate: ");

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onItemClick(int position) {
        Province province = provinces.get(position);
        AddPlaceActivity activity = (AddPlaceActivity) getActivity();
        activity.replaceFragment(CityFragment.newInstance(province.getCode()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(org.zackratos.weather.R.menu.add_place, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void queryPlace() {
        Observable.create(new ObservableOnSubscribe<List<Province>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Province>> e) throws Exception {
                List<Province> provinces = DataSupport.findAll(Province.class);
                e.onNext(provinces);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .compose(this.<List<Province>>bindToLifecycle())
                .map(new Function<List<Province>, List<Province>>() {
                    @Override
                    public List<Province> apply(@NonNull List<Province> provinces) throws Exception {
                        if (provinces != null && provinces.size() > 0) {
                            return provinces;
                        }
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.Http.PLACE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        PlaceApi api = retrofit.create(PlaceApi.class);
                        List<Province> mapProvinces = api.getProvinces()
                                .execute().body();
                        DataSupport.saveAll(mapProvinces);

                        return mapProvinces;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Province>>() {
                    @Override
                    public void accept(@NonNull List<Province> provinces) throws Exception {
                        ProvinceFragment.this.provinces = provinces;
                        PlaceAdapter<Province> adapter = new PlaceAdapter<>(provinces);
                        placeListView.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }




}

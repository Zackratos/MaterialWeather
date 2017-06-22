package org.zackratos.weather.addPlace.county;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.Constants;
import org.zackratos.weather.County;
import org.zackratos.weather.PlaceApi;
import org.zackratos.weather.addPlace.PlaceFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.zackratos.weather.Constants.AddPlace.CITY_ID;
import static org.zackratos.weather.Constants.AddPlace.PROVINCE_ID;
import static org.zackratos.weather.Constants.County.COUNTY_ID;

/**
 * Created by Administrator on 2017/6/19.
 */

public class CountyFragment extends PlaceFragment {

    public static CountyFragment newInstance(int provinceId, int cityId) {
        CountyFragment fragment = new CountyFragment();
        Bundle args = new Bundle();
        args.putInt(PROVINCE_ID, provinceId);
        args.putInt(CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }


    private List<County> counties;


    @Override
    protected void onItemClick(int position) {
        County county = counties.get(position);
        Intent intent = new Intent();
        intent.putExtra(COUNTY_ID, county.getId());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }


    public static int getCountyId(Intent intent) {

        return intent.getIntExtra(COUNTY_ID, 0);
    }



    @Override
    protected void queryPlace() {
        final int provinceId = getArguments().getInt(PROVINCE_ID, 0);
        final int cityId = getArguments().getInt(CITY_ID, 0);


        Observable.just(cityId)
                .subscribeOn(Schedulers.io())
                .compose(this.<Integer>bindToLifecycle())
                .map(new Function<Integer, List<County>>() {
                    @Override
                    public List<County> apply(@NonNull Integer integer) throws Exception {
                        return DataSupport.where("cityid = ?", String.valueOf(integer))
                                .find(County.class);
                    }
                })
                .map(new Function<List<County>, List<County>>() {
                    @Override
                    public List<County> apply(@NonNull List<County> counties) throws Exception {
                        if (counties != null && counties.size() > 0) {
                            return counties;
                        }

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.Http.PLACE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        PlaceApi api = retrofit.create(PlaceApi.class);
                        List<County> mapCounties = api.getCounties(provinceId, cityId)
                                .execute().body();
                        for (int i = 0; i < mapCounties.size(); i++) {
                            County county = mapCounties.get(i);
                            county.setCityId(cityId);
                            county.save();
                        }
                        return mapCounties;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<County>>() {
                    @Override
                    public void accept(@NonNull List<County> counties) throws Exception {
                        CountyFragment.this.counties = counties;
                        PlaceAdapter<County> adapter = new PlaceAdapter<>(counties);
                        placeListView.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}

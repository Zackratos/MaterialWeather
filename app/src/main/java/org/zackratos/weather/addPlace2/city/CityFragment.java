package org.zackratos.weather.addPlace2.city;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.City;

import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.PlaceApi;

import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.addPlace2.PlaceAdapter;
import org.zackratos.weather.addPlace2.PlaceFragment;
import org.zackratos.weather.addPlace2.county.CountyFragment;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.zackratos.weather.Constants.AddPlace.PROVINCE_ID;

/**
 * Created by Administrator on 2017/6/19.
 */

public class CityFragment extends PlaceFragment<City> {

    public static CityFragment newInstance(int provinceId) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(PROVINCE_ID, provinceId);
        fragment.setArguments(args);
        return fragment;
    }



    private int provinceId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provinceId = getArguments().getInt(PROVINCE_ID, 0);

        getActivity().setTitle("");
    }


    @Override
    protected void onItemClick(PlaceAdapter<City> adapter, int position) {
        City city = adapter.getData().get(position);
        callback.replaceFragment(CountyFragment
                .newInstance(provinceId, city.getCode()));
    }

    @Override
    protected void queryPlace() {
        Observable.just(provinceId)
                .subscribeOn(Schedulers.io())
                .compose(this.<Integer>bindToLifecycle())
                .map(new Function<Integer, List<City>>() {
                    @Override
                    public List<City> apply(@NonNull Integer integer) throws Exception {
                        return DataSupport.where("provinceid = ?", String.valueOf(integer))
                                .find(City.class);
                    }
                })
                .map(new Function<List<City>, List<City>>() {
                    @Override
                    public List<City> apply(@NonNull List<City> cities) throws Exception {
                        if (cities != null && cities.size() > 0) {
                            return cities;
                        }
                        Retrofit retrofit = HttpUtils.getPlaceRetrofit();
                        PlaceApi api = retrofit.create(PlaceApi.class);

                        List<City> mapCities = api.city(provinceId).execute().body();
                        for (int i = 0; i < mapCities.size(); i++) {
                            City city = mapCities.get(i);
                            city.setProvinceId(provinceId);
                            city.save();
                        }

                        return mapCities;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(@NonNull List<City> cities) throws Exception {
                        updateUI(cities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SingleToast.getInstance(getActivity())
                                .show(getString(R.string.add_place_get_place_fail,
                                        getString(R.string.add_place_city)));
                    }
                });

    }


    @Override
    protected void refreshPlace() {

    }
}

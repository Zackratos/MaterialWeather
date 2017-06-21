package org.zack.weather.addPlace.city;

import android.os.Bundle;

import org.litepal.crud.DataSupport;
import org.zack.weather.City;
import org.zack.weather.Constants;
import org.zack.weather.PlaceApi;
import org.zack.weather.addPlace.AddPlaceActivity;
import org.zack.weather.addPlace.PlaceFragment;
import org.zack.weather.addPlace.county.CountyFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.zack.weather.Constants.AddPlace.PROVINCE_ID;

/**
 * Created by Administrator on 2017/6/19.
 */

public class CityFragment extends PlaceFragment {

    public static CityFragment newInstance(int provinceId) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(PROVINCE_ID, provinceId);
        fragment.setArguments(args);
        return fragment;
    }


    private List<City> cities;


    @Override
    protected void onItemClick(int position) {
        City city = cities.get(position);
        AddPlaceActivity activity = (AddPlaceActivity) getActivity();
        activity.replaceFragment(CountyFragment.newInstance(
                getArguments().getInt(PROVINCE_ID, 0),
                city.getCode()
        ));
    }

    @Override
    protected void queryPlace() {
        final int provinceId = getArguments().getInt(PROVINCE_ID, 0);
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
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.Http.PLACE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        PlaceApi api = retrofit.create(PlaceApi.class);

                        List<City> mapCities = api.getCities(provinceId).execute().body();
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
                        CityFragment.this.cities = cities;
                        PlaceAdapter<City> adapter = new PlaceAdapter<>(cities);
                        placeListView.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }


}

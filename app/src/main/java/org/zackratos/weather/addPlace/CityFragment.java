package org.zackratos.weather.addPlace;

import android.os.Bundle;

import org.zackratos.weather.City;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CityFragment extends PlaceFragment<City> {


    public static final String PROVINCE_ID = "provinceId";

    public static CityFragment newInstance(int provinceId) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(PROVINCE_ID, provinceId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onItemClick(City place) {

    }

    @Override
    protected PlacePresenter<City> getPresenter() {

        return new PlacePresenter<>(this, new CityModel(getArguments().getInt(PROVINCE_ID)));
    }
}

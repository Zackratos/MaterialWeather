package org.zackratos.weather.addPlace;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zackratos.weather.R;


/**
 * Created by Administrator on 2017/7/7.
 */

public class ProvinceFragment extends PlaceFragment<Province> {



    public static ProvinceFragment newInstance() {
        return new ProvinceFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.add_place_label);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected PlaceContract.Presenter getPresenter() {
        return new PlacePresenter<Province>(new ProvinceModel());
    }







    @Override
    public void onItemClick(Province place) {
        callback.replaceFragment(CityFragment.newInstance(place.getId()));
    }
}

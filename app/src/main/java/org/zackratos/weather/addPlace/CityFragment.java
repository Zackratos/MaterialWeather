package org.zackratos.weather.addPlace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CityFragment extends PlaceFragment<City> {


    public static final String ID = "id";

    public static CityFragment newInstance(int provinceId) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putInt(ID, provinceId);
        fragment.setArguments(args);
        return fragment;
    }


    private Province province;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        int id = getArguments().getInt(ID);
        province = DataSupport.find(Province.class, id);
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(province.getName());

        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onItemClick(City place) {
        callback.replaceFragment(CountyFragment.newInstance(place.getId()));
    }



    @Override
    protected PlaceContract.Presenter getPresenter() {
        return new PlacePresenter<City>(new CityModel(province.getCode()));
    }




    /*    @Override
    protected PlacePresenter<City> getPresenter() {

        return new PlacePresenter<>(new CityModel(province.getCode()));
    }*/
}

package org.zackratos.weather.addPlace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.City;
import org.zackratos.weather.County;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CountyFragment extends PlaceFragment<County> {


    public static final String ID = "id";


    public static CountyFragment newInstance(int id) {

        CountyFragment fragment = new CountyFragment();

        Bundle args = new Bundle();

        args.putInt(ID, id);

        fragment.setArguments(args);

        return fragment;

    }


    private City city;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        int id = getArguments().getInt(ID);

        city = DataSupport.find(City.class, id);

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(city.getName());

        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onItemClick(County place) {

        callback.addCounty(place);

    }



    @Override
    protected PlaceContract.Presenter getPresenter() {
        return new PlacePresenter<County>(new CountyModel(city));
    }



    /*    @Override
    protected PlacePresenter<County> getPresenter() {
        return new PlacePresenter<>(new CountyModel(city));
    }*/
}

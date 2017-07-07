package org.zackratos.weather.addPlace;


import org.zackratos.weather.Province;



/**
 * Created by Administrator on 2017/7/7.
 */

public class ProvinceFragment extends PlaceFragment<Province> {



    public static ProvinceFragment newInstance() {
        return new ProvinceFragment();
    }



    @Override
    protected PlacePresenter<Province> getPresenter() {
        return new PlacePresenter<>(this, new ProvinceModel());
    }




    @Override
    public void onItemClick(Province place) {
        callback.replaceFragment(CityFragment.newInstance(place.getCode()));
    }
}

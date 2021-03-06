package org.zackratos.weather.addPlace;

import android.util.Log;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.HttpUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CityModel extends PlaceModel<City> {

    private int provinceId;

    public CityModel(int provinceId) {
        this.provinceId = provinceId;
    }



    @Override
    protected List<City> placesDB() {
        return DataSupport
                .where("provinceid = ?", String.valueOf(provinceId))
                .find(City.class);
    }


    @Override
    protected List<City> placesLine() {
        Log.d("TAG", "placesLine: ");
        call = HttpUtils.getPlaceRetrofit()
                .create(PlaceApi.class)
                .city(provinceId);
        try {
            List<City> cities = call.execute().body();
            return cities;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void savePlaces(List<City> places) {
        for (City city : places) {
            List<City> cs = DataSupport.select("id")
                    .where("code = ?", String.valueOf(city.getCode()))
                    .find(City.class);
            if (cs == null || cs.isEmpty()) {
                city.setProvinceId(provinceId);
                city.save();
            }
        }
    }


    /*    @Override
    protected void updatePlaces() {
        List<City> cities = placesLine();

        if (cities == null) {
            return;
        }

        for (City city : cities) {
            List<City> cs = DataSupport.select("id")
                    .where("code = ?", String.valueOf(city.getCode()))
                    .find(City.class);
            if (cs == null || cs.isEmpty()) {
                city.setProvinceId(provinceId);
                city.save();
            }
        }

        getPlacesDB();
    }*/


}

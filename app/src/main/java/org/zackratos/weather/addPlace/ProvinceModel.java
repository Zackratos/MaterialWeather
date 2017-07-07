package org.zackratos.weather.addPlace;


import org.litepal.crud.DataSupport;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.PlaceApi;
import org.zackratos.weather.Province;

import java.io.IOException;
import java.util.List;


/**
 * Created by Administrator on 2017/7/7.
 */

public class ProvinceModel extends PlaceModel<Province> {


    @Override
    protected List<Province> placesDB() {
        return DataSupport.findAll(Province.class);
    }


    protected List<Province> placesLine() {
        call = HttpUtils.getPlaceRetrofit()
                .create(PlaceApi.class)
                .provinces();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void updatePlaces() {
        List<Province> provinces = placesLine();

        if (provinces == null) {
            return;
        }

        for (Province province : provinces) {
            List<Province> ps = DataSupport.select("id")
                    .where("code = ?", String.valueOf(province.getCode()))
                    .find(Province.class);

            if (ps == null || ps.isEmpty()) {
                province.save();
            }
        }

        getPlacesDB();
    }





/*    @Override
    public List<Province> refreshPlaces() {

        List<Province> provinces = placesLine();

        for (Province province : provinces) {
            List<Province> ps = DataSupport.select("id")
                    .where("code = ?", String.valueOf(province.getCode()))
                    .find(Province.class);

            if (ps == null || ps.isEmpty()) {
                province.save();
            }
        }

        getPlacesDB();

        return places;


    }*/

}
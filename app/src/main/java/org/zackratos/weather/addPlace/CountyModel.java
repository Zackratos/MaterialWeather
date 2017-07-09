package org.zackratos.weather.addPlace;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.City;
import org.zackratos.weather.County;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.PlaceApi;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CountyModel extends PlaceModel<County> {

    private int provinceId, cityId;

    public CountyModel(City city) {
        this.provinceId = city.getProvinceId();
        this.cityId = city.getCode();
    }






    @Override
    protected List<County> placesDB() {
        return DataSupport
                .where("cityid = ?", String.valueOf(cityId))
                .find(County.class);
    }




    @Override
    protected List<County> placesLine() {
        call = HttpUtils.getPlaceRetrofit()
                .create(PlaceApi.class)
                .county(provinceId, cityId);
        try {
            List<County> counties = call.execute().body();
            return counties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void savePlaces(List<County> places) {
        for (County county : places) {
            List<County> cs = DataSupport.select("id")
                    .where("weatherid = ?", county.getWeatherId())
                    .find(County.class);
            if (cs == null || cs.isEmpty()) {
                county.setCityId(cityId);
                county.save();
            }

        }
    }





}

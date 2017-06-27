package org.zackratos.weather.hewind;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class HeWind {

    @SerializedName("HeWeather5")
    private List<HeWeather> heWeathers;


    public List<HeWeather> getHeWeathers() {
        return heWeathers;
    }
}

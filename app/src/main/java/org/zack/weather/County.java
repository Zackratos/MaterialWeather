package org.zack.weather;

import com.google.gson.annotations.SerializedName;

import org.zack.weather.addPlace.Place;

/**
 * Created by Administrator on 2017/6/19.
 */

public class County extends Place {


    @SerializedName("weather_id")
    private String weatherId;


    private int cityId;


    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}

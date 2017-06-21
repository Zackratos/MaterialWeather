package org.zack.weather;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/6/17.
 */

public interface CityListApi {


    @GET("http://mobile.weather.com.cn/js/citylist.xml")
    List<String> getCityList();
}

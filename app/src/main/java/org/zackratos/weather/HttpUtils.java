package org.zackratos.weather;

import org.zackratos.weather.Constants.Http;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/24.
 */

public class HttpUtils {

    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public static Retrofit getPlaceRetrofit() {
        return getRetrofit(Http.PLACE_URL);
    }



    public static Retrofit getSeniverseRetrofit() {
        return getRetrofit(Http.SENIVERSE_URL);
    }

    public static Retrofit getHeWindRetrofit() {
        return getRetrofit(Http.HE_WIND_URL);
    }


    public static Map<String, String> getSeniverseMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "2zxo2z17nleuu0w7");
        map.put("location", "");
        map.put("language", "zh-Hans");

        return map;
    }


    public static Map<String, String> getHeWindMap(String city) {
        Map<String, String> map = new HashMap<>();
        map.put("city", city);
        map.put("key", "cc2c7cf7bf2c49239b54b8c93f96ed33");

        return map;
    }



}

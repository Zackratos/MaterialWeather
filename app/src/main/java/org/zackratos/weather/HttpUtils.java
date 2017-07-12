package org.zackratos.weather;

import org.zackratos.weather.Constants.Http;
import org.zackratos.weather.addPlace.PlaceApi;
import org.zackratos.weather.weather.HeWindApi;
import org.zackratos.weather.weather.HeWindCache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
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
        return getRetrofit(PlaceApi.PLACE_URL);
    }




    public static Retrofit getHeWindRetrofit() {
        return getRetrofit(Http.HE_WIND_URL);
    }


    public static HeWindApi getHeWindApi() {
        return getHeWindRetrofit().create(HeWindApi.class);
    }

    public static HeWindCache getHeWindCache(File dir) {

        return new RxCache.Builder()
                .persistence(dir, new GsonSpeaker())
                .using(HeWindCache.class);

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

package org.zackratos.weather;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/25.
 */

public interface SeniverseApi {

    @GET("weather/now.json")
    Observable getNow(@QueryMap Map<String, String> map);
}

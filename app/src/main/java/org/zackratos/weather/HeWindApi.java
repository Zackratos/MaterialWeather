package org.zackratos.weather;

import org.zackratos.weather.hewind.now.HeNow;
import org.zackratos.weather.hewind.srarch.HeSearch;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/25.
 */

public interface HeWindApi {

    @GET("weather")
    Observable getWeather(@QueryMap Map<String, String> map);

    @GET("search")
    Observable<HeSearch> rxSearch(@QueryMap Map<String, String> map);

    @GET("search")
    Call<HeSearch> search(@QueryMap Map<String, String> map);

    @GET("now")
    Observable<HeNow> rxNow(@QueryMap Map<String, String> map);

    @GET("now")
    Call<HeNow> now(@QueryMap Map<String, String> map);
}

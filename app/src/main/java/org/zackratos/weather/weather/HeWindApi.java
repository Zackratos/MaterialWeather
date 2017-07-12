package org.zackratos.weather.weather;

import org.zackratos.weather.hewind.HeWind;
import org.zackratos.weather.hewind.srarch.HeSearch;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/25.
 */

public interface HeWindApi {

    @GET("weather")
    Observable<HeWind> rxWeather(@QueryMap Map<String, String> map);

    @GET("weather")
    Observable<ResponseBody> rxWeatherBody(@QueryMap Map<String, String> map);

    @GET("weather")
    Call<Weather> weather(@QueryMap Map<String, String> map);

    @GET("search")
    Observable<HeSearch> rxSearch(@QueryMap Map<String, String> map);

    @GET("search")
    Call<HeSearch> search(@QueryMap Map<String, String> map);



}

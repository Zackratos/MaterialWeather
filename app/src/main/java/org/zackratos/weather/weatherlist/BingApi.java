package org.zackratos.weather.weatherlist;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface BingApi {

    public static final String BING_PIC = "http://guolin.tech/";

    @GET("api/bing_pic")
    Call<ResponseBody> address();

    @GET("api/bing_pic")
    Observable<ResponseBody> rxAddress();

}

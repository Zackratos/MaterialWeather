package org.zackratos.weather;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface BingApi {


    @GET("api/bing_pic")
    Call<ResponseBody> address();

    @GET("api/bing_pic")
    Observable<ResponseBody> rxAddress();

}

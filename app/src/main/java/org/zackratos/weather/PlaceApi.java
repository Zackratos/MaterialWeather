package org.zackratos.weather;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/6/17.
 */

public interface PlaceApi {



    @GET("api/china")
    Call<List<Province>> getProvincesCall();

    @GET("api/china/{provinceId}")
    Call<List<City>> getCitiesCall(@Path("provinceId") int provinceId);

    @GET("api/china/{provinceId}/{cityId}")
    Call<List<County>> getCountiesCall(@Path("provinceId") int provinceId, @Path("cityId") int cityId);


    @GET("api/china")
    Observable<List<Province>> getProvinces();
}

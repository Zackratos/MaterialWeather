package org.zackratos.weather.addPlace;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/6/17.
 */

public interface PlaceApi {

    public static final String PLACE_URL = "http://guolin.tech/";


    @GET("api/china")
    Call<List<Province>> provinces();

    @GET("api/china/{provinceId}")
    Call<List<City>> city(@Path("provinceId") int provinceId);

    @GET("api/china/{provinceId}/{cityId}")
    Call<List<County>> county(@Path("provinceId") int provinceId, @Path("cityId") int cityId);


    @GET("api/china")
    Observable<List<Province>> rxProvinces();
}

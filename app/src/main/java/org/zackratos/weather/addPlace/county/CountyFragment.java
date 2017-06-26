package org.zackratos.weather.addPlace.county;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.County;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.PlaceApi;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.addPlace.PlaceAdapter;
import org.zackratos.weather.addPlace.PlaceFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.zackratos.weather.Constants.AddPlace.CITY_ID;
import static org.zackratos.weather.Constants.AddPlace.PROVINCE_ID;

/**
 * Created by Administrator on 2017/6/19.
 */

public class CountyFragment extends PlaceFragment<County> {

    public static CountyFragment newInstance(int provinceId, int cityId) {
        CountyFragment fragment = new CountyFragment();
        Bundle args = new Bundle();
        args.putInt(PROVINCE_ID, provinceId);
        args.putInt(CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }


    private int provinceId;

    private int cityId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provinceId = getArguments().getInt(PROVINCE_ID, 0);
        cityId = getArguments().getInt(CITY_ID, 0);
    }


    @Override
    protected void onItemClick(PlaceAdapter<County> adapter, int position) {
        County county = adapter.getData().get(position);
        callback.addCounty(county.getId());
    }







    @Override
    protected void queryPlace() {
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<County> counties = HttpUtils.getPlaceRetrofit()
                            .create(PlaceApi.class)
                            .getCountiesCall(provinceId, cityId)
                            .execute().body();

                    updateUI(counties);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        Observable.just(cityId)
                .subscribeOn(Schedulers.io())
                .compose(this.<Integer>bindToLifecycle())
                .map(new Function<Integer, List<County>>() {
                    @Override
                    public List<County> apply(@NonNull Integer integer) throws Exception {
                        return DataSupport.where("cityid = ?", String.valueOf(integer))
                                .find(County.class);
                    }
                })
                .map(new Function<List<County>, List<County>>() {
                    @Override
                    public List<County> apply(@NonNull List<County> counties) throws Exception {
                        if (counties != null && counties.size() > 0) {
                            return counties;
                        }
//                        Thread.sleep(5000);
                        Retrofit retrofit = HttpUtils.getPlaceRetrofit();
                        PlaceApi api = retrofit.create(PlaceApi.class);
                        List<County> mapCounties = api.getCountiesCall(provinceId, cityId)
                                .execute().body();
                        for (int i = 0; i < mapCounties.size(); i++) {
                            County county = mapCounties.get(i);
                            county.setCityId(cityId);
                            county.save();
                        }
                        return mapCounties;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<County>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<County> counties) {
                        updateUI(counties);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Context context = getActivity();
                        if (context != null) {
                            SingleToast.getInstance(context)
                                    .show(getString(R.string.add_place_get_place_fail,
                                            getString(R.string.add_place_county)));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void refreshPlace() {

    }


    private Disposable disposable;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}

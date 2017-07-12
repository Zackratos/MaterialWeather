package org.zackratos.weather.addPlace2.province;

import android.os.Bundle;
import android.support.annotation.Nullable;


import org.litepal.crud.DataSupport;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.addPlace.PlaceApi;
import org.zackratos.weather.addPlace.Province;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.addPlace2.PlaceAdapter;
import org.zackratos.weather.addPlace2.PlaceFragment;
import org.zackratos.weather.addPlace2.city.CityFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/17.
 */

public class ProvinceFragment extends PlaceFragment<Province> {


    public static ProvinceFragment newInstance() {
        return new ProvinceFragment();
    }


    private ProvincePresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProvincePresenter(null);

        getActivity().setTitle(R.string.add_place_label);
    }


    @Override
    protected void onItemClick(PlaceAdapter<Province> adapter, int position) {
        Province province = adapter.getData().get(position);
        callback.replaceFragment(CityFragment.newInstance(province.getCode()));
    }




    @Override
    protected void queryPlace() {
        Observable.create(new ObservableOnSubscribe<List<Province>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Province>> e) throws Exception {
                List<Province> provinces = DataSupport.findAll(Province.class);
                e.onNext(provinces);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .compose(this.<List<Province>>bindToLifecycle())
                .map(new Function<List<Province>, List<Province>>() {
                    @Override
                    public List<Province> apply(@NonNull List<Province> provinces) throws Exception {
                        if (provinces != null && provinces.size() > 0) {
                            return provinces;
                        }
                        PlaceApi api = HttpUtils.getPlaceRetrofit().create(PlaceApi.class);
                        List<Province> mapProvinces = api.provinces()
                                .execute().body();
                        DataSupport.saveAll(mapProvinces);
                        return mapProvinces;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Province>>() {
                    @Override
                    public void accept(@NonNull List<Province> provinces) throws Exception {
                        updateUI(provinces);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SingleToast.getInstance(getActivity())
                                .show(getString(R.string.add_place_get_place_fail,
                                        getString(R.string.add_place_province)));
                    }
                });

    }


    @Override
    protected void refreshPlace() {
        HttpUtils.getPlaceRetrofit()
                .create(PlaceApi.class)
                .rxProvinces()
                .subscribeOn(Schedulers.io())
                .compose(this.<List<Province>>bindToLifecycle())
                .flatMap(new Function<List<Province>, ObservableSource<Province>>() {
                    @Override
                    public ObservableSource<Province> apply(@NonNull List<Province> provinces) throws Exception {
                        return Observable.fromIterable(provinces);
                    }
                })
                .filter(new Predicate<Province>() {
                    @Override
                    public boolean test(@NonNull Province province) throws Exception {

                        List<Province> provinces = DataSupport.select()
                                .where("code = ?", String.valueOf(province.getCode()))
                                .find(Province.class);
                        return provinces == null || provinces.size() == 0;
                    }
                })
                .doOnNext(new Consumer<Province>() {
                    @Override
                    public void accept(@NonNull Province province) throws Exception {
                        province.save();
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        updateProvince();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Province>() {
                    @Override
                    public void accept(@NonNull Province province) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        refreshLayout.setRefreshing(false);
                        SingleToast.getInstance(getActivity()).show(R.string.add_place_refresh_fail);
                    }
                });

    }




    private void updateProvince() {
        Observable.create(new ObservableOnSubscribe<List<Province>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Province>> e) throws Exception {
                List<Province> provinces = DataSupport.findAll(Province.class);
                e.onNext(provinces);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Province>>() {
                    @Override
                    public void accept(@NonNull List<Province> provinces) throws Exception {
                        updateUI(provinces);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }




}

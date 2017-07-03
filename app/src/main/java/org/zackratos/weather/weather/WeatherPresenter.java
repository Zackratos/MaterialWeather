package org.zackratos.weather.weather;


import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.HeWind;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * Created by Administrator on 2017/6/27.
 */

public class WeatherPresenter {

    private static final String TAG = "TAG";

    private WeatherView view;

    public WeatherPresenter(WeatherView view) {
        this.view = view;
    }


    private Disposable disposable;


    public void initWeather(String weatherId) {

        refreshWeather(weatherId, false);

    }




    public void refreshWeather(String weatherId, boolean update) {
        HttpUtils.getHeWindCache(view.cacheFile())
                .rxWeather(HttpUtils.getHeWindApi().rxWeather(HttpUtils.getHeWindMap(weatherId)),
                        new DynamicKey(weatherId), new EvictDynamicKey(update))
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Reply<HeWind>, ObservableSource<HeWeather>>() {
                    @Override
                    public ObservableSource<HeWeather> apply(@NonNull Reply<HeWind> heWindReply) throws Exception {
                        HeWeather heWeather = heWindReply.getData().getHeWeathers().get(0);
                        String status = heWeather.getStatus();
                        if ("ok".equals(status)) {
                            return Observable.just(heWeather);
                        }
                        return Observable.error(new Throwable(status));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull HeWeather heWeather) {
                        view.updateUI(heWeather);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.updateFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }






    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    public void detach() {
        view = null;
    }


}

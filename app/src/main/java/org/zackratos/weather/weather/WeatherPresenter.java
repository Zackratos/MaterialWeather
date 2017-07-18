package org.zackratos.weather.weather;

import android.content.Context;
import android.os.AsyncTask;

import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.HeWind;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherPresenter extends WeatherContract.Presenter {

    private WeatherContract.Model model;


    public WeatherPresenter() {
        model = new WeatherModel();
    }



    @Override
    void initWeather(final Context context, final String weatherId) {

        new AsyncTask<Void, Void, Weather>() {
            @Override
            protected Weather doInBackground(Void... params) {
                return model.initWeather(weatherId);
            }

            @Override
            protected void onPostExecute(Weather weather) {
                if (view == null) {
                    return;
                }
                view.weatherInited(weather);
                refreshWeatherInfo(context, false);
            }
        }.execute();

    }





    @Override
    void refreshWeatherInfo(final Context context, final boolean update) {

        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                e.onNext(model.cacheFolder(context));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .flatMap(new Function<File, ObservableSource<Reply<HeWind>>>() {
                    @Override
                    public ObservableSource<Reply<HeWind>> apply(@NonNull File file) throws Exception {

                        return HttpUtils.getHeWindCache(file)
                                .rxWeather(HttpUtils.getHeWindApi().rxWeather(HttpUtils.getHeWindMap(model.getWeather().getWeatherId())),
                                        new DynamicKey(model.getWeather().getWeatherId()),
                                        new EvictDynamicKey(update));
                    }
                })
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
                .subscribe(new Consumer<HeWeather>() {
                    @Override
                    public void accept(@NonNull HeWeather heWeather) throws Exception {
                        view.onWeatherInfoUpdate(heWeather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.requestError(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                        WeatherPresenter.this.disposable = disposable;
                    }
                });

    }


    private Disposable disposable;




    @Override
    void cancelRequest() {
//        model.cancelRequest();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

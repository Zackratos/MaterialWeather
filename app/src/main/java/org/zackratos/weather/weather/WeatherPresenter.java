package org.zackratos.weather.weather;

import android.util.Log;

import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.hewind.HeWind;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
/*        HttpUtils.getHeWindApi()
                .rxWeatherBody(HttpUtils.getHeWindMap(weatherId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        Log.d(TAG, "accept: " + responseBody.string());
                    }
                });*/

        HttpUtils.getHeWindApi()
                .rxWeather(HttpUtils.getHeWindMap(weatherId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeWind>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull HeWind heWind) {
                        Log.d(TAG, "onNext: " + heWind.getHeWeathers().get(0).getNow().getCond().getTxt());
                        view.updateUI(heWind);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
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

}

package org.zackratos.weather.weatherlist;

import android.content.Context;

import org.zackratos.weather.Weather;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherListPresenter extends WeatherListContract.Presenter {

    private WeatherListContract.Model model;


    @Override
    protected void onDetach() {
        super.onDetach();
        model = null;
    }



    public WeatherListPresenter(Context context) {

        model = new WeatherListModel(context);

    }


    @Override
    void setHeader() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(model.getHeaderUrl());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        view.setHeader(s);
                    }
                });
    }



    @Override
    void initWeathers() {


        view.onWeatherChecked(model.getWeatherId());

        Observable.create(new ObservableOnSubscribe<List<Weather>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Weather>> e) throws Exception {
                model.initWeathers();
                e.onNext(model.getWeathers());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Weather>>() {
                    @Override
                    public void accept(@NonNull List<Weather> weathers) throws Exception {
                        view.updateWeathers(weathers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }




    @Override
    void clickWeather(int position) {
        String weatherId = model.getWeather(position).getWeatherId();
        model.putWeatherId(weatherId);
        view.updateWeathers(model.getWeathers());
        view.onWeatherChecked(weatherId);
    }



    @Override
    void deleteWeather(int position) {

    }
}

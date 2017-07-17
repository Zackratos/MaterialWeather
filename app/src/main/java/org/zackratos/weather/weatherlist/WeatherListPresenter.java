package org.zackratos.weather.weatherlist;

import android.app.Activity;
import android.content.Context;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.weather.HeWindApi;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.hewind.srarch.HeSearch;
import org.zackratos.weather.hewind.srarch.SearchBasic;
import org.zackratos.weather.hewind.srarch.SearchHeWeather5;
import org.zackratos.xmaplocation.LocateListener;
import org.zackratos.xmaplocation.Location;
import org.zackratos.xmaplocation.XMapLocation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
    void locate(Activity activity) {
        XMapLocation.newBuilder(activity)
                .build()
                .locate(new LocateListener() {
                    @Override
                    public void onLocated(Location location) {
                        if (!location.isSuccess()) {
                            view.showError(location.getErrorInfo());
                            return;
                        }


                        locateSuccess(location);
                    }
                });
    }



    private void locateSuccess(Location location) {
        HttpUtils.getHeWindRetrofit()
                .create(HeWindApi.class)
                .rxSearch(HttpUtils.getHeWindMap(location.getLongitude() + "," + location.getLatitude()))
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<HeSearch, ObservableSource<SearchBasic>>() {
                    @Override
                    public ObservableSource<SearchBasic> apply(@NonNull HeSearch heSearch) throws Exception {
                        SearchHeWeather5 heWeather5 = heSearch.getHeWeather5().get(0);
                        String status = heWeather5.getStatus();
                        if (status.equals("ok")) {
                            return Observable.just(heWeather5.getBasic());
                        }
                        return Observable.error(new Throwable(status));
                    }
                })
                .doOnNext(new Consumer<SearchBasic>() {
                    @Override
                    public void accept(@NonNull SearchBasic searchBasic) throws Exception {
                        model.saveLocation(searchBasic);
                    }
                })
                .subscribe(new Observer<SearchBasic>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        model.setDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull SearchBasic searchBasic) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.onLocate();
                    }
                });
    }




    @Override
    void cancelRequest() {
        if (model != null) {
            model.dispose();
        }
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }


    public static final String NO_WEATHER = "no_weather";



    @Override
    void initWeathers(final Activity activity) {

//        view.onWeatherChecked(model.getWeatherId());
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(model.getWeatherId());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<Weather>>() {
                    @Override
                    public ObservableSource<Weather> apply(@NonNull String s) throws Exception {
                        List<Weather> weathers =  DataSupport
                                .where("weatherid = ?", s)
                                .find(Weather.class);
                        if (weathers == null || weathers.isEmpty()) {
                            return Observable.error(new Throwable(NO_WEATHER));
                        }
                        return Observable.just(weathers.get(0));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather>() {
                    @Override
                    public void accept(@NonNull Weather weather) throws Exception {
                        view.onWeatherChecked(weather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (NO_WEATHER.equals(throwable.getMessage())){
                            locate(activity);
                        }
                    }
                });



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
//        String weatherId = model.getWeather(position).getWeatherId();
        Weather weather = model.getWeather(position);
        model.putWeatherId(weather.getWeatherId());
        view.updateWeathers(model.getWeathers());
        view.onWeatherChecked(weather);
    }



    @Override
    void deleteWeather(int position) {

    }


    @Override
    void drag(int from, int to) {
        model.switchWeather(from, to);
    }
}

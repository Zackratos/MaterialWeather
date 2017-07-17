package org.zackratos.weather.weather;

import android.content.Context;

import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/7/9.
 */

public interface WeatherContract {

    interface Model extends BaseModel {
        Weather initWeather(String weatherId);
        File cacheFolder(Context context);
//        void setDisposable(Disposable disposable);
//        void cancelRequest();
        Weather getWeather();
    }

    interface View extends BaseView {
        void weatherInited(Weather weather);
        void onWeatherInfoUpdate(HeWeather weather);
        void requestError(String message);
    }


    abstract class Presenter extends BasePresenter<View> {

        abstract void initWeather(Context context, String weatherId);

        abstract void refreshWeatherInfo(Context context, boolean update);

        abstract void cancelRequest();
    }


}

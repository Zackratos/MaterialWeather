package org.zackratos.weather.weatherlist;

import android.app.Activity;

import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.hewind.srarch.SearchBasic;
import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/7/9.
 */

public interface WeatherListContract {

    interface View extends BaseView {
        void onLocate();
        void setHeader(String url);
        void updateWeathers(List<Weather> weathers);
        void onWeatherChecked(Weather weather);
        void showError(String message);

    }



    interface Model extends BaseModel {
        void saveLocation(SearchBasic searchBasic);
        void setDisposable(Disposable disposable);
        void dispose();
        String getHeaderUrl();
        void initWeathers();
        List<Weather> getWeathers();
        Weather getWeather(int position);
        void putWeatherId(String weatherId);
        String getWeatherId();
        void deleteWeather(int position);
    }





    abstract class Presenter extends BasePresenter<View> {
        abstract void cancelRequest();
        abstract void locate(Activity activity);
        abstract void setHeader();
        abstract void initWeathers(Activity activity);
        abstract void clickWeather(int position);
        abstract void deleteWeather(int position);
//        abstract void addWeather(int countyId);
    }



}

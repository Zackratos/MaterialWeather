package org.zackratos.weather.weatherlist;

import org.zackratos.weather.Weather;
import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public interface WeatherListContract {

    interface View extends BaseView {

        void setHeader(String url);
        void updateWeathers(List<Weather> weathers);
        void onWeatherChecked(String weatherId);

    }



    interface Model extends BaseModel {
        String getHeaderUrl();
        void initWeathers();
        List<Weather> getWeathers();
        Weather getWeather(int position);
        void putWeatherId(String weatherId);
        String getWeatherId();
        void deleteWeather(int position);
    }





    abstract class Presenter extends BasePresenter<View> {
        abstract void setHeader();
        abstract void initWeathers();
        abstract void clickWeather(int position);
        abstract void deleteWeather(int position);
//        abstract void addWeather(int countyId);
    }



}

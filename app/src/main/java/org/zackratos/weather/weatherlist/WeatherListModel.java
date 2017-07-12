package org.zackratos.weather.weatherlist;

import android.content.Context;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.SPUtils;
import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.hewind.srarch.SearchBasic;

import java.io.IOException;
import java.util.List;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherListModel implements WeatherListContract.Model {


    private Context context;

    private WeatherArrayList<Weather> weathers;

    public WeatherListModel(Context context) {
        this.context = context;
    }


    @Override
    public void saveLocation(SearchBasic searchBasic) {
        int index = SPUtils.getWeatherIndex(context);
        Weather weather = new Weather.Builder()
                .weatherId(searchBasic.getId())
                .countyName(searchBasic.getCity())
                .index(index)
                .build();
        weather.saveOrUpdate("weatherid = ?", searchBasic.getId());
        SPUtils.putWeatherId(context, searchBasic.getId());
        SPUtils.putWeatherIndex(context, ++index);
    }


    private Disposable disposable;


    @Override
    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;

    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }



    @Override
    public void initWeathers() {
        List<Weather> ws = DataSupport.order("id").find(Weather.class);
        if (weathers == null) {
            weathers = new WeatherArrayList<>(new WeatherArrayList.OnRemoveListener() {
                @Override
                public void onRemove(Object o) {
                    ((Weather) o).delete();
                }
            });
        } else {
            weathers.clear();
        }

        weathers.addAll(ws);

    }



    @Override
    public String getHeaderUrl() {

        try {
            ResponseBody body = HttpUtils.getRetrofit(BingApi.BING_PIC)
                    .create(BingApi.class)
                    .address().execute().body();
            if (body != null) {
                String url = body.string();
                SPUtils.putBingAdd(context, url);
                return url;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return SPUtils.getBingAdd(context);
    }




    @Override
    public List<Weather> getWeathers() {

        return weathers;
    }


    @Override
    public Weather getWeather(int position) {
        if (weathers != null && weathers.size() > position) {
            return weathers.get(position);
        }

        return null;
    }


/*    @Override
    public void addWeather(int countyId) {
        County county = DataSupport.find(County.class, countyId);
        ContentValues values = new ContentValues();
        values.put("checked", false);
        DataSupport.updateAll(Weather.class, values, "weatherid != ?", county.getWeatherId());
        Weather weather = new Weather.Builder()
                .weatherId(county.getWeatherId())
                .countyName(county.getName())
                .checked(true)
                .build();
        weather.saveOrUpdate("weatherid = ?", county.getWeatherId());
    }*/



    @Override
    public void putWeatherId(String weatherId) {
        SPUtils.putWeatherId(context, weatherId);
    }



    @Override
    public String getWeatherId() {
        return SPUtils.getWeatherId(context);
    }



    @Override
    public void deleteWeather(int position) {

    }
}

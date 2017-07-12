package org.zackratos.weather.weather;

import android.content.Context;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherModel implements WeatherContract.Model {

    private Weather weather;

    private Disposable disposable;




    @Override
    public Weather initWeather(String weatherId) {
        List<Weather> weathers = DataSupport
                .where("weatherid = ?", weatherId)
                .find(Weather.class);

        if (weathers != null && !weathers.isEmpty()) {
            weather = weathers.get(0);
        }

        return weather;
    }


    @Override
    public Weather getWeather() {
        return weather;
    }

    @Override
    public File cacheFolder(Context context) {
        Log.d("TAG", "cacheFolder: " + Thread.currentThread().getName());
        File file = new File(context.getExternalCacheDir(), "heWeather");
        if (!file.exists()) {
            file.mkdir();
        } else {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdir();
            }
        }

        return file;
    }




    @Override
    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;

    }


    @Override
    public void cancelRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

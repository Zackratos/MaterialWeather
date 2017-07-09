package org.zackratos.weather.weather2;

import org.zackratos.weather.hewind.HeWeather;

import java.io.File;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface WeatherView {

    void updateUI(HeWeather weather);

    void updateFail(String message);

    File cacheFile();
}

package org.zackratos.weather.weather;

import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.HeWind;

import java.io.File;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface WeatherView {

    void updateUI(HeWeather weather);

    void updateFail(String message);

    File cacheFile();
}

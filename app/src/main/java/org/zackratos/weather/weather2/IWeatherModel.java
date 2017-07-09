package org.zackratos.weather.weather2;

import org.zackratos.weather.hewind.HeWeather;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface IWeatherModel {

    HeWeather getWeather(String weatherId);

}

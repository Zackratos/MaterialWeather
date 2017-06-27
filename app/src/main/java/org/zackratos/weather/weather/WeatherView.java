package org.zackratos.weather.weather;

import org.zackratos.weather.hewind.HeWind;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface WeatherView {

    void updateUI(HeWind heWind);

    void updateFail(String message);
}

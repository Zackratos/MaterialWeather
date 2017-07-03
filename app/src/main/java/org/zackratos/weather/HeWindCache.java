package org.zackratos.weather;

import org.zackratos.weather.hewind.HeWind;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface HeWindCache {


    @LifeCache(duration = 30, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HeWind>> rxWeather(Observable<HeWind> weather,
                                        DynamicKey dynamicKey,
                                        EvictDynamicKey evictDynamicKey);



}

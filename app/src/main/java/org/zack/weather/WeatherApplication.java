package org.zack.weather;

import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2017/6/17.
 */

public class WeatherApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(this);
    }
}

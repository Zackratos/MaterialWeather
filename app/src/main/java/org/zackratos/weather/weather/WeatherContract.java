package org.zackratos.weather.weather;

import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

/**
 * Created by Administrator on 2017/7/9.
 */

public interface WeatherContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }


    abstract class Presenter extends BasePresenter<View> {

    }


}

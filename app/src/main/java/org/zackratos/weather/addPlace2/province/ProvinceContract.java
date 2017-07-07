package org.zackratos.weather.addPlace2.province;

import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface ProvinceContract {


    interface View extends BaseView {

    }


    interface Model extends BaseModel {


    }


    abstract class Presenter extends BasePresenter<View> {

    }



}

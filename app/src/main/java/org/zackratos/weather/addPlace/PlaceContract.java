package org.zackratos.weather.addPlace;

import org.zackratos.weather.Place;
import org.zackratos.weather.mvp.BaseModel;
import org.zackratos.weather.mvp.BasePresenter;
import org.zackratos.weather.mvp.BaseView;

import java.util.List;


/**
 * Created by Administrator on 2017/7/7.
 */

public interface PlaceContract {

    interface View<P extends Place> extends BaseView {

        void setPlaces(List<P> places);

        void onItemClick(P place);

        void refreshError(String msg);
    }


    interface Model<P extends Place> extends BaseModel {


        List<P> initPlaces();
        void cancelRequest();
        List<P> refreshPlaces();
        P getPlace(int position);

    }



    abstract class Presenter extends BasePresenter<View> {

        abstract void initPlace();

        abstract void cancelRequest();

        abstract void refreshPlace();

        abstract void clickItem(int position);
    }


}

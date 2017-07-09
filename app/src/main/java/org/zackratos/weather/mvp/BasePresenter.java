package org.zackratos.weather.mvp;

/**
 * Created by Administrator on 2017/7/6.
 */

public class BasePresenter<V extends BaseView> {

    protected V view;

    protected void onAttach(V view) {
        this.view = view;
    }

    protected void onDetach() {
        this.view = null;
    }


}

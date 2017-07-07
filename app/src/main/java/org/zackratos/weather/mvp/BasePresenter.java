package org.zackratos.weather.mvp;

/**
 * Created by Administrator on 2017/7/6.
 */

public abstract class BasePresenter<V extends BaseView> {
    private V view;

    protected void onAttach(V view) {
        this.view = view;
    }

    protected void onDetach() {
        this.view = null;
    }


}

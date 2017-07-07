package org.zackratos.weather.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.zackratos.weather.BaseFragment;

/**
 * Created by Administrator on 2017/7/6.
 */

public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {


    protected P presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = getPresenter();

        presenter.onAttach((V) this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDetach();
        }

    }



    protected abstract P getPresenter();
}

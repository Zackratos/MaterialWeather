package org.zackratos.weather.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.zackratos.weather.BaseActivity;

/**
 * Created by Administrator on 2017/7/6.
 */

public abstract class MvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        presenter = getPresenter();
        presenter.onAttach((V) this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    protected abstract P getPresenter();

}

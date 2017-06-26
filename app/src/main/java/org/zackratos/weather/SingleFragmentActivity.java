package org.zackratos.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 */

public abstract class SingleFragmentActivity extends BaseActivity{




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm
                .findFragmentById(R.id.weather_container);

        if (fragment == null) {
            fragment = createFragment();
        }

        fm.beginTransaction()
                .add(R.id.weather_container, fragment)
                .commit();

    }



    protected abstract BaseFragment createFragment();

    protected void showToolbar() {

        LinearLayout parentView = ButterKnife.findById(this, R.id.fragment_parent);

        Toolbar toolbar = (Toolbar) LayoutInflater.from(this)
                .inflate(R.layout.toolbar, parentView, false);

        parentView.addView(toolbar, 0);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

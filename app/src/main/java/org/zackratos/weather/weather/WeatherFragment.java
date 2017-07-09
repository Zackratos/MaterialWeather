package org.zackratos.weather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.zackratos.weather.R;
import org.zackratos.weather.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherFragment extends MvpFragment<WeatherContract.View, WeatherContract.Presenter> {

    public static String WEATHER_ID = "weather_id";

    public static WeatherFragment newInstance(String weatherId) {

        WeatherFragment fragment = new WeatherFragment();

        Bundle args = new Bundle();

        args.putString(WEATHER_ID, weatherId);

        fragment.setArguments(args);

        return fragment;

    }



    @BindView(R.id.weather_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.weather_item_container)
    LinearLayout itemContainer;



    Unbinder unbinder;





    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new WeatherPresenter();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }



    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshLayout.setRefreshing(false);
        refreshLayout.destroyDrawingCache();
        refreshLayout.clearAnimation();
        unbinder.unbind();
    }









    interface Callback {

    }


    private Callback callback;



}

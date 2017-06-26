package org.zackratos.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zackratos.weather.Constants.Weather.WEATHER_ID;

/**
 * Created by Administrator on 2017/6/25.
 */

public class WeatherFragment extends BaseFragment {


    public static WeatherFragment newInstance(int weatherId) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt(WEATHER_ID, weatherId);
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.weather_refresh)
    SwipeRefreshLayout refreshLayout;


    Unbinder unbinder;
    private Callback callback;
    private Weather weather;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int weatherId = getArguments().getInt(WEATHER_ID, 0);

        weather = DataSupport.find(Weather.class, weatherId);

        callback.setName(weather.getCountyName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
            }
        };
        refreshLayout.setOnRefreshListener(listener);
        refreshLayout.setRefreshing(true);
        listener.onRefresh();

        return view;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface Callback {
        void setName(String name);
    }
}

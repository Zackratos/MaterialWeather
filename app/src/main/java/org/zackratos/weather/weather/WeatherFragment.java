package org.zackratos.weather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.BaseFragment;
import org.zackratos.weather.R;
import org.zackratos.weather.Weather;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.HeWind;
import org.zackratos.weather.hewind.Now;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zackratos.weather.Constants.Weather.WEATHER_ID;

/**
 * Created by Administrator on 2017/6/25.
 */

public class WeatherFragment extends BaseFragment implements WeatherView {


    public static WeatherFragment newInstance(int weatherId) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt(WEATHER_ID, weatherId);
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.weather_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.weather_daily_container)
    LinearLayout dailyContainer;


    Unbinder unbinder;
    private Callback callback;
    private Weather weather;
    private WeatherPresenter presenter;


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

        presenter = new WeatherPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        presenter.initWeather(weather.getWeatherId());
        return view;
    }



    private void initView() {
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        };

        refreshLayout.setOnRefreshListener(listener);


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }



    public interface Callback {
        void setName(String name);

        void setNowInfo(Now now);
    }









    @Override
    public void updateUI(HeWind heWind) {
        callback.setNowInfo(heWind.getHeWeathers().get(0).getNow());
    }


    @Override
    public void updateFail(String message) {

    }
}

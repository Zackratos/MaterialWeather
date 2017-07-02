package org.zackratos.weather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.BaseFragment;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.Weather;
import org.zackratos.weather.hewind.Daily;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.Now;
import org.zackratos.weather.hewind.Suggestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zackratos.weather.Constants.Http.HE_WIND_ICON;
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


    @BindView(R.id.weather_item_container)
    LinearLayout itemContainer;



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
        presenter.detach();
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
    public void updateUI(HeWeather weather) {
        callback.setNowInfo(weather.getNow());
        callback.setName(weather.getBasic().getCity());
        List<Daily> dailies = weather.getDailies();
        for (Daily daily : dailies) {
            CardView dailyView = (CardView) LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_weather_daily, itemContainer, false);
            TextView dateView = ButterKnife.findById(dailyView, R.id.daily_date);
            dateView.setText(daily.getDate());


            ImageView dayIcon = ButterKnife.findById(dailyView, R.id.daily_day_icon);
            Glide.with(this).load(HE_WIND_ICON + daily.getCond().getCode_d() + ".png").into(dayIcon);

            ImageView nightIcon = ButterKnife.findById(dailyView, R.id.daily_night_icon);
            Glide.with(this).load(HE_WIND_ICON + daily.getCond().getCode_n() + ".png").into(nightIcon);

            TextView tempView = ButterKnife.findById(dailyView, R.id.daily_temp);
            tempView.setText(daily.getTmp().getMin() + "°" + " - " + daily.getTmp().getMax() + "°");

            dailyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DailyDialog dialog = DailyDialog.newInstance();

                    dialog.show(getFragmentManager(), "daily");
                }
            });

            itemContainer.addView(dailyView);
        }


        Suggestion suggestion = weather.getSuggestion();


        CardView suggestionView = (CardView) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_weather_suggestion, itemContainer, false);

        TextView titleView = ButterKnife.findById(suggestionView, R.id.suggestion_title);
        titleView.setText("舒适度指数");
        TextView brfView = ButterKnife.findById(suggestionView, R.id.suggestion_brf);
        brfView.setText(suggestion.getComf().getBrf());
        TextView txtView = ButterKnife.findById(suggestionView, R.id.suggestion_txt);
        txtView.setText(suggestion.getComf().getTxt());

        itemContainer.addView(suggestionView);





    }




    @Override
    public void updateFail(String message) {
        SingleToast.getInstance(getActivity()).show(message);
    }
}

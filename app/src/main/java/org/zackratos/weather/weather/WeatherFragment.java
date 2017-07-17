package org.zackratos.weather.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.hewind.Daily;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.Hourly;
import org.zackratos.weather.hewind.Now;
import org.zackratos.weather.hewind.Suggestion;
import org.zackratos.weather.hewind.Wind;
import org.zackratos.weather.mvp.MvpFragment;
import org.zackratos.weather.service.WeatherService;
import org.zackratos.weather.weather2.DailyDialog;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zackratos.weather.Constants.Http.HE_WIND_ICON;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherFragment extends MvpFragment<WeatherContract.View, WeatherContract.Presenter>
        implements WeatherContract.View {

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
        String weatherId = getArguments().getString(WEATHER_ID);
        refreshLayout.setColorSchemeResources(R.color.main);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshWeatherInfo(getActivity(), true);
            }
        });

        presenter.initWeather(getActivity(), weatherId);

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelRequest();
    }





    public interface Callback {
        void setName(String name);

        void setNowInfo(Now now);

//        void setBackground(String code);
    }


    private Callback callback;







    /////////////////////////////////


    @Override
    public void weatherInited(Weather weather) {

        callback.setName(weather.getCountyName());
        callback.setNowInfo(null);
    }


    @Override
    public void onWeatherInfoUpdate(HeWeather weather) {
        callback.setName(weather.getBasic().getCity());
        callback.setNowInfo(weather.getNow());
        itemContainer.removeAllViews();
        addHours(weather.getHourlies());
        addDailies(weather.getDailies());
        addNow(weather.getNow());
        addWind(weather.getNow().getWind());
        addSuggestion(weather.getSuggestion());
        refreshLayout.setRefreshing(false);
        getActivity().startService(WeatherService.newIntent(getActivity(),
                weather.getBasic().getCity(), weather.getNow()));
    }


    private void addHours(final List<Hourly> hourlies) {
        if (hourlies == null || hourlies.isEmpty()) {
            return;
        }

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_weather_hour, itemContainer, false);
        itemContainer.addView(view);
        LinearLayout hourContainer = ButterKnife.findById(view, R.id.hour_container);

        for (Hourly hourly : hourlies) {

            CardView hourView = (CardView) LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_weather_hour_item, hourContainer, false);
            TextView tempView = ButterKnife.findById(hourView, R.id.hour_temp);
            tempView.setText(hourly.getTmp() + "°");
            TextView timeView = ButterKnife.findById(hourView, R.id.hour_time);
            timeView.setText(hourly.getDate().split(" ")[1]);
            ImageView iconView = ButterKnife.findById(hourView, R.id.hour_icon);
            Glide.with(this).load(HE_WIND_ICON + hourly.getCond().getCode() + ".png").into(iconView);

            hourContainer.addView(hourView);
        }


    }





    private void addDailies(List<Daily> dailies) {


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
    }



    private void addSuggestion(Suggestion suggestion) {

        addSuggestionItem(suggestion.getComf(), R.string.weather_comf);
        addSuggestionItem(suggestion.getCw(), R.string.weather_cw);
        addSuggestionItem(suggestion.getDrsg(), R.string.weather_drsg);
        addSuggestionItem(suggestion.getFlu(), R.string.weather_flu);
        addSuggestionItem(suggestion.getSport(), R.string.weather_sport);
        addSuggestionItem(suggestion.getTrav(), R.string.weather_trav);
        addSuggestionItem(suggestion.getUv(), R.string.weather_uv);

    }


    private void addSuggestionItem(Suggestion.SuggestionBean bean, @StringRes int titleId) {
        CardView suggestionView = (CardView) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_weather_suggestion, itemContainer, false);
        TextView titleView = ButterKnife.findById(suggestionView, R.id.suggestion_title);
        titleView.setText(getString(titleId));
        TextView brfView = ButterKnife.findById(suggestionView, R.id.suggestion_brf);
        brfView.setText(bean.getBrf());
        TextView txtView = ButterKnife.findById(suggestionView, R.id.suggestion_txt);
        txtView.setText(bean.getTxt());
        itemContainer.addView(suggestionView);

    }



    private void addNow(Now now) {
        CardView nowView = (CardView) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_weather_now, itemContainer, false);
        TextView flView = ButterKnife.findById(nowView, R.id.now_fl);
        flView.setText(now.getFl());
        TextView humView = ButterKnife.findById(nowView, R.id.now_hum);
        humView.setText(now.getHum() + " %");
        TextView pcpnView = ButterKnife.findById(nowView, R.id.now_pcpn);
        pcpnView.setText(now.getPcpn() + " mm");
        TextView presView = ButterKnife.findById(nowView, R.id.now_pres);
        presView.setText(now.getPres());
        TextView visView = ButterKnife.findById(nowView, R.id.now_vis);
        visView.setText(now.getVis() + " km");
        itemContainer.addView(nowView);
    }



    private void addWind(Wind wind) {
        CardView windView = (CardView) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_weather_wind, itemContainer, false);

        TextView degView = ButterKnife.findById(windView, R.id.wind_deg);
        degView.setText(wind.getDeg() + " / 360°");

        TextView dirView = ButterKnife.findById(windView, R.id.wind_dir);
        dirView.setText(wind.getDir());

        TextView scView = ButterKnife.findById(windView, R.id.wind_sc);
        scView.setText(wind.getSc());

        TextView spdView = ButterKnife.findById(windView, R.id.wind_spd);
        spdView.setText(wind.getSpd() + " kmph");

        itemContainer.addView(windView);
    }





    @Override
    public void requestError(String message) {
        refreshLayout.setRefreshing(false);
        callback.setNowInfo(null);
        itemContainer.removeAllViews();
        SingleToast.getInstance(getActivity()).show(message);
    }
}

package org.zackratos.weather.weather2;

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

import org.litepal.crud.DataSupport;
import org.zackratos.weather.BaseFragment;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.Weather;
import org.zackratos.weather.hewind.Daily;
import org.zackratos.weather.hewind.HeWeather;
import org.zackratos.weather.hewind.Hourly;
import org.zackratos.weather.hewind.Now;
import org.zackratos.weather.hewind.Suggestion;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zackratos.weather.Constants.Http.HE_WIND_ICON;

/**
 * Created by Administrator on 2017/6/25.
 */

public class WeatherFragment extends BaseFragment implements WeatherView {

    public static final String WEATHER_ID = "weather_id";


    public static WeatherFragment newInstance(int weatherSid) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt(WEATHER_ID, weatherSid);
        fragment.setArguments(args);
        return fragment;
    }



    public static WeatherFragment newInstance(String weatherId) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle ars = new Bundle();
        ars.putString(WEATHER_ID, weatherId);
        fragment.setArguments(ars);
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


    private boolean noData;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int weatherSid = getArguments().getInt(WEATHER_ID, 0);

//        weather = DataSupport.find(Weather.class, weatherSid);
        String weatherId = getArguments().getString(WEATHER_ID, null);
        if (weatherId == null) {
            noData = true;
            return;
        }

        List<Weather> weathers = DataSupport
                .where("weatherid = ?", weatherId)
                .find(Weather.class);
        if (weathers == null || weathers.isEmpty()) {
            noData = true;
            return;
        }

        weather = weathers.get(0);


        callback.setName(weather.getCountyName());

        callback.setNowInfo(null);

        presenter = new WeatherPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (noData) {
            return view;
        }


        initView();

        presenter.initWeather(weather.getWeatherId());
        return view;
    }



    private void initView() {
        refreshLayout.setColorSchemeResources(R.color.main);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshWeather(weather.getWeatherId(), true);
            }
        };

        refreshLayout.setOnRefreshListener(listener);

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            refreshLayout.destroyDrawingCache();
            refreshLayout.clearAnimation();
        }
        unbinder.unbind();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dispose();
            presenter.detach();
        }
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

        itemContainer.removeAllViews();
        callback.setNowInfo(weather.getNow());
        callback.setName(weather.getBasic().getCity());

        addHour(weather);

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


        addSuggestion(weather);

        refreshLayout.setRefreshing(false);

    }



    private void addHour(HeWeather weather) {
        List<Hourly> hourlies = weather.getHourlies();
        if (hourlies == null || hourlies.size() == 0) {
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


    private void addSuggestion(HeWeather weather) {
        Suggestion suggestion = weather.getSuggestion();

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







    @Override
    public void updateFail(String message) {
        refreshLayout.setRefreshing(false);
        SingleToast.getInstance(getActivity()).show(message);
    }



    @Override
    public File cacheFile() {
        File file = new File(getActivity().getExternalCacheDir(), "heWeather");
        if (!file.exists()) {
            file.mkdir();
        } else {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdir();
            }
        }

        return file;
    }
}

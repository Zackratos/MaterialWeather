package org.zackratos.weather.weatherlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.litepal.crud.DataSupport;
import org.zackratos.weather.BaseFragment;
import org.zackratos.weather.BingApi;
import org.zackratos.weather.Constants;
import org.zackratos.weather.County;
import org.zackratos.weather.HttpUtils;
import org.zackratos.weather.R;
import org.zackratos.weather.SPUitls;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.Weather;
import org.zackratos.weather.addPlace.AddPlaceActivity;
import org.zackratos.weather.hewind.srarch.SearchBasic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DrawerFragment extends BaseFragment {


    public static DrawerFragment newInstance() {
        return new DrawerFragment();
    }



    @BindView(R.id.drawer_county_list)
    RecyclerView countyListView;

    @BindView(R.id.drawer_header)
    ImageView headerView;


    @OnClick(R.id.drawer_fab)
    void onFabClick() {
        startActivityForResult(AddPlaceActivity.newIntent(getActivity()), Constants.Drawer.REQUEST_CODE);

    }



    Unbinder unbinder;


    private WeatherAdapter adapter;

    private Callback callback;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }




    private void initView() {
        countyListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Observable.create(new ObservableOnSubscribe<List<Weather>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Weather>> e) throws Exception {
                List<Weather> weathers = DataSupport.select("weatherid", "countyname")
                        .find(Weather.class);
                e.onNext(weathers);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Weather>>() {
                    @Override
                    public void accept(@NonNull List<Weather> weathers) throws Exception {
                        updateUI(weathers);
                    }
                });


        Glide.with(this).load(SPUitls.getBingAdd(getActivity())).into(headerView);


        HttpUtils.getRetrofit(Constants.Http.BING_PIC)
                .create(BingApi.class)
                .rxAddress()
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(@NonNull ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        SPUitls.putBingAdd(getActivity(), s);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Glide.with(DrawerFragment.this)
                                .load(s)
                                .into(headerView);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }



    private void updateUI(List<Weather> weathers) {
        if (adapter == null) {
            adapter = new WeatherAdapter(weathers);
            countyListView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Weather weather = (Weather) adapter.getData().get(position);
                    for (Weather w : (List<Weather>)adapter.getData()) {
                        w.setChecked(false);
                    }
                    callback.switchWeather(weather.getId());
                    weather.setChecked(true);
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            adapter.setNewData(weathers);
            adapter.notifyDataSetChanged();
            if (countyListView.getAdapter() == null) {
                countyListView.setAdapter(adapter);
            }
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == Constants.Drawer.REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    addSelectWeather(data);
                    break;
                case Constants.AddPlace.LOCATE_RESULT:
                    addLocateWeather(data);
                    break;
                default:
                    break;
            }

        }


    }



    private void addSelectWeather(Intent intent) {
        Observable.just(intent)
                .subscribeOn(Schedulers.io())
                .map(new Function<Intent, Integer>() {
                    @Override
                    public Integer apply(@NonNull Intent intent) throws Exception {

                        return AddPlaceActivity.getCountyId(intent);
                    }
                })
                .map(new Function<Integer, County>() {
                    @Override
                    public County apply(@NonNull Integer integer) throws Exception {

                        return DataSupport.find(County.class, integer);
                    }
                })
                .doOnNext(new Consumer<County>() {
                    @Override
                    public void accept(@NonNull County county) throws Exception {
                        List<Weather> weathers = adapter.getData();
                        for (int i = 0; i < weathers.size(); i++) {
                            Weather weather = weathers.get(i);
                            if (weather.getWeatherId().equals(county.getWeatherId())) {
                                return;
                            }
                        }
                        Weather weather = new Weather.Builder()
                                .countyName(county.getName())
                                .weatherId(county.getWeatherId())
                                .build();
                        adapter.getData().add(weather);
                        weather.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<County>() {
                    @Override
                    public void accept(@NonNull County county) throws Exception {
                        adapter.notifyItemInserted(adapter.getData().size() - 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }



    private void addLocateWeather(Intent intent) {
        Observable.just(intent)
                .subscribeOn(Schedulers.io())
                .map(new Function<Intent, SearchBasic>() {
                    @Override
                    public SearchBasic apply(@NonNull Intent intent) throws Exception {
                        return AddPlaceActivity.getBasic(intent);
                    }
                })
                .filter(new Predicate<SearchBasic>() {
                    @Override
                    public boolean test(@NonNull SearchBasic searchBasic) throws Exception {
                        List<Weather> weathers = DataSupport.select("weatherid").find(Weather.class);
                        for (int i = 0; i < weathers.size(); i++) {
                            Weather weather = weathers.get(i);
                            if (weather.getWeatherId().equals(searchBasic.getId())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .doOnNext(new Consumer<SearchBasic>() {
                    @Override
                    public void accept(@NonNull SearchBasic searchBasic) throws Exception {
                        Weather weather = new Weather.Builder()
                                .weatherId(searchBasic.getId())
                                .countyName(searchBasic.getCity())
                                .build();
                        adapter.getData().add(weather);
                        weather.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchBasic>() {
                    @Override
                    public void accept(@NonNull SearchBasic searchBasic) throws Exception {
                        adapter.notifyItemInserted(adapter.getData().size() - 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }










    private class WeatherAdapter extends BaseQuickAdapter<Weather, BaseViewHolder> {
        public WeatherAdapter(@Nullable List<Weather> data) {
            super(R.layout.item_drawer, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, Weather item) {
            helper.setText(R.id.drawer_item_name, item.getCountyName())
                    .setChecked(R.id.drawer_item_name, item.isChecked());
        }
    }



    public interface Callback {
        void switchWeather(int id);
    }


}

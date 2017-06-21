package org.zack.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.litepal.crud.DataSupport;
import org.zack.weather.addPlace.AddPlaceActivity;
import org.zack.weather.addPlace.county.CountyFragment;

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

import static org.zack.weather.Constants.Drawer.REQUEST_CODE;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DrawerFragment extends BaseFragment {


    public static DrawerFragment newInstance() {
        return new DrawerFragment();
    }



    @BindView(R.id.drawer_county_list)
    RecyclerView countyListView;


    @OnClick(R.id.drawer_fab)
    void onFabClick() {
        startActivityForResult(AddPlaceActivity.newIntent(getActivity()), REQUEST_CODE);

    }



    Unbinder unbinder;


//    private List<County> counties;
    private List<Weather> weathers;
    private WeatherAdapter adapter;




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

        countyListView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Weather weather = weathers.get(position);

            }
        });

        Observable.create(new ObservableOnSubscribe<List<Weather>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Weather>> e) throws Exception {
                List<Weather> weathers = DataSupport.select("countyname")
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }



    private void updateUI(List<Weather> weathers) {
        this.weathers = weathers;
        if (adapter == null) {
            adapter = new WeatherAdapter(weathers);
            countyListView.setAdapter(adapter);
        } else {
            adapter.setNewData(weathers);
            adapter.notifyDataSetChanged();
        }
    }








    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {

        Observable.just(data)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Intent>() {
                    @Override
                    public boolean test(@NonNull Intent intent) throws Exception {
                        return requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK;
                    }
                })
                .map(new Function<Intent, Integer>() {
                    @Override
                    public Integer apply(@NonNull Intent intent) throws Exception {
                        
                        return CountyFragment.getCountyId(intent);
                    }
                })
                .map(new Function<Integer, County>() {
                    @Override
                    public County apply(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "apply: " + integer);
                        return DataSupport.find(County.class, integer);
                    }
                })
                .map(new Function<County, Weather>() {
                    @Override
                    public Weather apply(@NonNull County county) throws Exception {

                        Weather weather = new Weather.Builder()
                                .countyName(county.getName())
                                .build();
                        weathers.add(weather);
                        weather.save();
                        return weather;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather>() {
                    @Override
                    public void accept(@NonNull Weather weather) throws Exception {
                        adapter.notifyItemInserted(weathers.size() - 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: throw");
                    }
                });

    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





    private class WeatherAdapter extends BaseQuickAdapter<Weather, BaseViewHolder> {
        public WeatherAdapter(@Nullable List<Weather> data) {
            super(R.layout.item_drawer, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Weather item) {
            helper.setText(R.id.drawer_item_name, item.getCountyName());
        }
    }


}

package org.zackratos.weather.weatherlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.melnykov.fab.FloatingActionButton;

import org.zackratos.weather.Constants;
import org.zackratos.weather.R;
import org.zackratos.weather.SPUtils;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.weather.Weather;
import org.zackratos.weather.addPlace.AddPlaceActivity;
import org.zackratos.weather.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherListFragment extends MvpFragment<WeatherListContract.View, WeatherListContract.Presenter>
        implements WeatherListContract.View {


    public static WeatherListFragment newInstance() {

        return new WeatherListFragment();
    }



    @BindView(R.id.drawer_header)
    ImageView headerView;

    @BindView(R.id.drawer_county_list)
    RecyclerView weatherListView;

    @BindView(R.id.drawer_fab)
    FloatingActionButton addButton;

    Unbinder unbinder;


    private WeatherAdapter adapter;


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

        weatherListView.setLayoutManager(new LinearLayoutManager(getActivity()));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddPlaceActivity.newIntent(getActivity()), 0);
            }
        });

        weatherListView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                callback.closeDrawer(true);
                presenter.clickWeather(position);
            }
        });


        presenter.setHeader();
        presenter.initWeathers(getActivity());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            switch (resultCode) {

                case Constants.AddPlace.LOCATE_RESULT:
                    callback.closeDrawer(false);
                    presenter.initWeathers(getActivity());
                    break;

                case Constants.AddPlace.SELECT_RESULT:
                    callback.closeDrawer(false);
//                    int countyId = AddPlaceActivity.getCountyId(data);
//                    presenter.addWeather(countyId);
                    presenter.initWeathers(getActivity());
                    break;

                default:
                    break;
            }

        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.cancelRequest();
        }
    }






    public interface Callback {
        void onWeatherChecked(Weather weather);

        void closeDrawer(boolean animate);
    }

    private Callback callback;









    @Override
    protected WeatherListContract.Presenter getPresenter() {
        return new WeatherListPresenter(getActivity());
    }







///////////////////////////////////////////////////////






    @Override
    public void setHeader(String url) {
        Glide.with(this).load(url).into(headerView);
    }



    private OnItemDragListener dragListener;

    private OnItemSwipeListener swipeListener;



    private void initDragListener() {
        if (dragListener == null) {

            dragListener = new OnItemDragListener() {
                @Override
                public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                    from = pos;
                }

                @Override
                public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
//                    Log.d(TAG, "onItemDragMoving: " + from + " " + to);
                    presenter.drag(from, to);
                }

                @Override
                public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

                }
            };
        }
    }


    private void initSwipeListener() {
        if (swipeListener == null) {
            swipeListener = new OnItemSwipeListener() {
                @Override
                public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                    Log.d(TAG, "onItemSwipeStart: ");
                }

                @Override
                public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

                    Log.d(TAG, "clearView: ");
                }

                @Override
                public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

                }

                @Override
                public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder,
                                              float dX, float dY, boolean isCurrentlyActive) {
//                    Log.d(TAG, "onItemSwipeMoving: ");
                }
            };
        }
    }




    @Override
    public void updateWeathers(List<Weather> weathers) {
        String weatherId = SPUtils.getWeatherId(getActivity());
        if (adapter == null) {
            adapter = new WeatherAdapter(weathers, weatherId);
            weatherListView.setAdapter(adapter);
            ItemDragAndSwipeCallback callback = new ItemDragAndSwipeCallback(adapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(weatherListView);
            adapter.enableDragItem(helper);
            adapter.enableSwipeItem();
            initDragListener();
            initSwipeListener();
            adapter.setOnItemDragListener(dragListener);
            adapter.setOnItemSwipeListener(swipeListener);
        } else {
            adapter.setWeatherId(weatherId);
            adapter.setNewData(weathers);
            adapter.notifyDataSetChanged();
        }
//        callback.onWeatherChecked(weatherId);

    }



    @Override
    public void onWeatherChecked(Weather weather) {
        callback.onWeatherChecked(weather);
    }



    @Override
    public void showError(String message) {
        SingleToast.getInstance(getActivity()).show(message);
    }



    @Override
    public void onLocate() {
        presenter.initWeathers(getActivity());
    }
}

package org.zackratos.weather.addPlace;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.zackratos.weather.Place;
import org.zackratos.weather.R;
import org.zackratos.weather.SingleToast;
import org.zackratos.weather.mvp.MvpFragment;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/7.
 */

public abstract class PlaceFragment<P extends Place> extends MvpFragment<PlaceContract.View, PlaceContract.Presenter> implements PlaceContract.View<P> {


    Unbinder unbinder;



    @BindView(R.id.place_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.place_list)
    RecyclerView placeListView;


    private PlaceAdapter<P> adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (PlaceCallback) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place, container, false);

        unbinder = ButterKnife.bind(this, view);

        initView();

        presenter.initPlace();

        return view;
    }


    private void initView() {
        refreshLayout.setColorSchemeResources(R.color.main);

        placeListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPlace();
            }
        });

        placeListView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                presenter.clickItem(position);
            }
        });

        callback.initLocateButton(placeListView);
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


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }



    protected PlaceCallback callback;








    @Override
    public void setPlaces(List<P> places) {

        if (adapter == null) {
            adapter = new PlaceAdapter<>(places);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        } else {
            adapter.setNewData(places);
            adapter.notifyDataSetChanged();
        }

        if (placeListView.getAdapter() == null) {
            placeListView.setAdapter(adapter);
        }

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }





    @Override
    public void refreshError(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        SingleToast.getInstance(getActivity()).show(msg);
    }
}

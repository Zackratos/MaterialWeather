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

import org.zackratos.weather.BaseFragment;
import org.zackratos.weather.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/19.
 */

public abstract class PlaceFragment<T extends Place> extends BaseFragment {


    @BindView(R.id.place_refresh)
    protected SwipeRefreshLayout refreshLayout;

    @BindView(R.id.place_list)
    protected RecyclerView placeListView;


    Unbinder unbinder;

    protected PlaceCallback callback;


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
        View view = inflater.inflate(org.zackratos.weather.R.layout.fragment_place, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setColorSchemeResources(R.color.main);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPlace();
            }
        });
        placeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        placeListView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlaceFragment.this.onItemClick((PlaceAdapter<T>) adapter, position);
            }
        });


        callback.initLocateButton(placeListView);

        queryPlace();
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    protected abstract void onItemClick(PlaceAdapter<T> adapter, int position);

    protected abstract void queryPlace();

    protected abstract void refreshPlace();


    protected void updateUI(List<T> places) {
        PlaceAdapter<T> adapter = (PlaceAdapter<T>) placeListView.getAdapter();
        if (adapter == null) {
            adapter = new PlaceAdapter<>(places);
            placeListView.setAdapter(adapter);
        } else {
            adapter.setNewData(places);
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

package org.zack.weather.addPlace;

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

import org.zack.weather.BaseFragment;
import org.zack.weather.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/19.
 */

public abstract class PlaceFragment extends BaseFragment {


    @BindView(R.id.place_list)
    protected RecyclerView placeListView;


    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        unbinder = ButterKnife.bind(this, view);
        placeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        placeListView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlaceFragment.this.onItemClick(position);
            }
        });
        ((AddPlaceActivity) getActivity()).getLocateButton()
                .attachToRecyclerView(placeListView);
        queryPlace();
        return view;
    }



    protected abstract void onItemClick(int position);

    protected abstract void queryPlace();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    protected class PlaceAdapter<T extends Place> extends BaseQuickAdapter<T, BaseViewHolder> {
        public PlaceAdapter(@Nullable List<T> data) {
            super(R.layout.item_add_place, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            helper.setText(R.id.add_place_item_text, item.getName());
        }
    }


}

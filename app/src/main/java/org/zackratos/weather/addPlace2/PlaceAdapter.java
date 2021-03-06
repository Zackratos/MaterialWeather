package org.zackratos.weather.addPlace2;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.weather.addPlace.Place;
import org.zackratos.weather.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/24.
 */

public class PlaceAdapter<T extends Place> extends BaseQuickAdapter<T, BaseViewHolder> {
    public PlaceAdapter(@Nullable List<T> data) {
        super(R.layout.item_add_place, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        helper.setText(R.id.place_name, item.getName());
    }
}

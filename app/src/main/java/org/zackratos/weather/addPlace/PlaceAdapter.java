package org.zackratos.weather.addPlace;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.weather.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PlaceAdapter<P extends Place> extends BaseQuickAdapter<P, BaseViewHolder> {

    public PlaceAdapter(@Nullable List<P> data) {
        super(R.layout.item_add_place, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, P item) {
        helper.setText(R.id.place_name, item.getName());
    }
}

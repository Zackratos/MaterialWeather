package org.zackratos.weather.weatherlist;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.weather.R;
import org.zackratos.weather.weather.Weather;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class WeatherAdapter extends BaseItemDraggableAdapter<Weather, BaseViewHolder> {



//    private WeatherListFragment.Callback callback;


    private String weatherId;


    public WeatherAdapter(@Nullable List<Weather> data, String weatherId) {
        super(R.layout.item_drawer, data);
        this.weatherId = weatherId;
    }



    @Override
    protected void convert(BaseViewHolder helper, final Weather item) {
        helper.setText(R.id.drawer_item_name, item.getCountyName())
                .setChecked(R.id.drawer_item_name, weatherId.equals(item.getWeatherId()));
    }



    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }


}

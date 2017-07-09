package org.zackratos.weather.weatherlist;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/10.
 */

public class WeatherArrayList<T> extends ArrayList<T> {



    public WeatherArrayList(OnRemoveListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean remove(Object o) {
        listener.onRemove(o);
        return super.remove(o);
    }


    @Override
    public T remove(int index) {
        listener.onRemove(get(index));
        return super.remove(index);
    }



    private OnRemoveListener listener;


    interface OnRemoveListener {
        void onRemove(Object o);
    }




}

package org.zackratos.weather.addPlace;

import android.support.v7.widget.RecyclerView;

import org.zackratos.weather.County;


/**
 * Created by Administrator on 2017/7/8.
 */

public interface PlaceCallback {

    void initLocateButton(RecyclerView recyclerView);

    void replaceFragment(PlaceFragment fragment);

    void addCounty(County county);

}

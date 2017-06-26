package org.zackratos.weather.addPlace;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/6/24.
 */

public interface PlaceCallback {

    void initLocateButton(RecyclerView recyclerView);

    void replaceFragment(PlaceFragment fragment);

    void addCounty(int countyId);
}

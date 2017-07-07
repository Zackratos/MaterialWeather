package org.zackratos.weather.addPlace;


import org.zackratos.weather.Place;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/7/8.
 */

public abstract class PlaceModel<P extends Place> implements PlaceContract.Model<P> {


    protected List<P> places;

    protected Call<List<P>> call;


    protected abstract List<P> placesDB();


    protected abstract void updatePlaces();


    protected void getPlacesDB() {
        places = placesDB();
    }





    @Override
    public List<P> initPlaces() {
        getPlacesDB();
        if (places == null || places.isEmpty()) {
            updatePlaces();
        }

        return places;
    }



    @Override
    public void cancelRequest() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }





    @Override
    public P getPlace(int position) {

        return places.get(position);
    }



    @Override
    public List<P> refreshPlaces() {

        updatePlaces();

        return places;
    }
}

package org.zackratos.weather.addPlace;


import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/7/8.
 */

public abstract class PlaceModel<P extends Place> implements PlaceContract.Model<P> {


    protected List<P> places;

    protected Call<List<P>> call;


    protected abstract List<P> placesDB();

    protected abstract List<P> placesLine();

    protected abstract void savePlaces(List<P> places);


    protected void updatePlaces() {
        List<P> places = placesLine();
        if (places == null) {
            return;
        }
        savePlaces(places);
        getPlacesDB();
    }


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

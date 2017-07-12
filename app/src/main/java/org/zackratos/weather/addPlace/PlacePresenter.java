package org.zackratos.weather.addPlace;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PlacePresenter<P extends Place> extends PlaceContract.Presenter {

    private static final String TAG = "TAG";

    private Disposable d;

    private AsyncTask<Void, Void, List<P>> a;


    private PlaceContract.Model model;


    public PlacePresenter (PlaceContract.Model model) {

        this.model = model;

    }



    @Override
    void initPlace() {


        a = new AsyncTask<Void, Void, List<P>>() {
            @Override
            protected List<P> doInBackground(Void... params) {

                return model.initPlaces();
            }

            @Override
            protected void onPostExecute(List<P> ps) {
                Log.d(TAG, "onPostExecute: ");
                if (ps != null) {
                    view.setPlaces(ps);
                } else {
                    view.refreshError(null);
                }
            }
        };

        a.execute();
    }




    @Override
    void cancelRequest() {
        model.cancelRequest();

        if (a != null && !a.isCancelled()) {
            a.cancel(true);
        }

        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }




    @Override
    void refreshPlace() {
        Observable.create(new ObservableOnSubscribe<List<P>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<P>> e) throws Exception {
                e.onNext(model.refreshPlaces());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<P>>() {
                    @Override
                    public void accept(@NonNull List<P> ps) throws Exception {

                        view.setPlaces(ps);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.refreshError(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        PlacePresenter.this.d = disposable;
                    }
                });

    }




    @Override
    void clickItem(int position) {
        view.onItemClick(model.getPlace(position));
    }




}

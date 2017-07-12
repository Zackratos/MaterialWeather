package org.zackratos.weather.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


public class WeatherService extends Service {

    public static final String WEATHER_ID = "weather_id";

    public static Intent newIntent(Context context, String weatherId) {
        Intent intent = new Intent(context, WeatherService.class);
        intent.putExtra(WEATHER_ID, weatherId);
        return intent;
    }




    public WeatherService() {
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }











    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

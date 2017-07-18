package org.zackratos.weather.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

import org.zackratos.weather.MainActivity;
import org.zackratos.weather.R;
import org.zackratos.weather.hewind.Now;

import static org.zackratos.weather.Constants.Http.HE_WIND_ICON;


public class WeatherService extends Service {

//    public static final String WEATHER_ID = "weather_id";

    private static final String NAME = "name";
    private static final String NOW = "now";


    public static Intent newIntent(Context context) {
        return new Intent(context, WeatherService.class);
    }


    public static Intent newIntent(Context context, String name, Now now) {
        Intent intent = new Intent(context, WeatherService.class);
        intent.putExtra(NAME, name);
        intent.putExtra(NOW, now);
        return intent;
    }




    public WeatherService() {
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getStringExtra(NAME);
        Now now = intent.getParcelableExtra(NOW);
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_weather);
        rv.setTextViewText(R.id.notification_title, name);
        rv.setTextViewText(R.id.notification_content, String.format("%s  %s°C", now.getCond().getTxt(), now.getTmp()));

        PendingIntent pi = PendingIntent.getActivity(this, 0, MainActivity.newIntent(this), 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent(rv)
                .setContentIntent(pi)
                .build();
        NotificationTarget target = new NotificationTarget(
                this, rv, R.id.notification_image, notification, 1
        );
        Glide.with(this).load(String.format("%s%s.png", HE_WIND_ICON, now.getCond().getCode()))
                .asBitmap()
                .into(target);
        startForeground(1, notification);


        return super.onStartCommand(intent, flags, startId);
    }











    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

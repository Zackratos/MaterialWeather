package org.zackratos.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/7/3.
 */

public class SPUtils {

    private static final String BING_ADDRESS = "bing_address";
    private static final String WEATHER_SID = "weather_sid";
    private static final String WEATHER_ID = "weather_id";


    private static SharedPreferences getDSP(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    private static SharedPreferences.Editor getDEditor(Context context) {
        return getDSP(context).edit();
    }


    public static void putInt(Context context, String key, int value) {
        getDEditor(context).putInt(key, value).apply();
    }


    public static int getInt(Context context, String key) {
        return getDSP(context).getInt(key, 0);
    }


    public static void putWeatherSid(Context context, int weatherSid) {
        putInt(context, WEATHER_SID, weatherSid);
    }


    public static int getWeatherSid(Context context) {
        return getInt(context, WEATHER_SID);
    }


    public static void putWeatherId(Context context, String weatherId) {
        putString(context, WEATHER_ID, weatherId);
    }


    public static String getWeatherId(Context context) {
        return getString(context, WEATHER_ID);
    }




    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, "");
    }


    public static String getBingAdd(Context context) {
        return getString(context, BING_ADDRESS);
    }



    public static void putString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(key, value).apply();

    }



    public static void putBingAdd(Context context, String address) {
        putString(context, BING_ADDRESS, address);
    }


}

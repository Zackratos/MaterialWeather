package org.zackratos.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/7/3.
 */

public class SPUitls {


    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, "");
    }


    public static String getBingAdd(Context context) {
        return getString(context, Constants.SP.BING_ADDRESS);
    }



    public static void putString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(key, value).apply();

    }



    public static void putBingAdd(Context context, String address) {
        putString(context, Constants.SP.BING_ADDRESS, address);
    }


}

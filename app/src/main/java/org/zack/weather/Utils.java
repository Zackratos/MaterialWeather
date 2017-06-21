package org.zack.weather;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Utils {


    public static <T extends View> T inflater(Context context, @LayoutRes int resource,
                                              ViewGroup parent, boolean attachToRoot) {
        return (T) LayoutInflater.from(context).inflate(resource, parent, attachToRoot);
    }
}

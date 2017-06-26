package org.zackratos.weather;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/24.
 */

public class SingleToast {

    private Context context;
    private Toast toast;

    private SingleToast(Context context) {
        if (this.context == null)
            this.context = context.getApplicationContext();
    }

    private static SingleToast singleToast;


    public static SingleToast getInstance(Context context) {
        if (singleToast == null) {
            synchronized (SingleToast.class) {
                if (singleToast == null) {
                    singleToast = new SingleToast(context);
                }
            }
        }

        return singleToast;

    }


    public void show(@StringRes int stringId) {
        if (toast == null) {
            toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(stringId);
        }
        toast.show();
    }



    public void show(CharSequence content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }




/*    private static class NonClass {
        private static SingleToast singleToast;

        private static SingleToast getSingleToast(Context context) {
            if (singleToast == null) {
                singleToast = new SingleToast(context);
            }

            return singleToast;
        }

    }*/




}

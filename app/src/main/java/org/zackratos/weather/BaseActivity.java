package org.zackratos.weather;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Administrator on 2017/6/17.
 */

public class BaseActivity extends RxAppCompatActivity {

    protected static final String TAG = "TAG";






    protected boolean navigationBarExist() {
        WindowManager windowManager = getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }





    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }



    protected int getNavigationHeight() {

        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }



    protected View createStatusBarView(@ColorInt int color) {
        View mStatusBarTintView = new View(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        params.gravity = Gravity.TOP;
        mStatusBarTintView.setLayoutParams(params);
        mStatusBarTintView.setBackgroundColor(color);
        return mStatusBarTintView;
    }


    protected View createNavBarView(@ColorInt int color) {
        View mNavBarTintView = new View(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, getNavigationHeight());
        params.gravity = Gravity.BOTTOM;
        mNavBarTintView.setLayoutParams(params);
        mNavBarTintView.setBackgroundColor(color);
        return mNavBarTintView;
    }


}

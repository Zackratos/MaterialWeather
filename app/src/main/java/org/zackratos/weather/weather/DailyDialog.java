package org.zackratos.weather.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zackratos.weather.R;

/**
 * Created by Administrator on 2017/7/2.
 */

public class DailyDialog extends DialogFragment {

    public static DailyDialog newInstance() {
        return new DailyDialog();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_daily, container, false);

        return view;

    }
}

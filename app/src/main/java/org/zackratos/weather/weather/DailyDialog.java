package org.zackratos.weather.weather;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("sadjlkf")
                .setView(R.layout.dialog_daily)
                .create();


        return dialog;
    }
}

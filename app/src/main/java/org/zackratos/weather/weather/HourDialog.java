package org.zackratos.weather.weather;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.zackratos.weather.R;

/**
 * Created by Administrator on 2017/7/18.
 */

public class HourDialog extends DialogFragment {


    public static HourDialog newInstance() {

        HourDialog dialog = new HourDialog();

        return dialog;

    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_hour, null);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        return dialog;
    }
}

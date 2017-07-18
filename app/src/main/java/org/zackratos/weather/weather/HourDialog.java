package org.zackratos.weather.weather;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.zackratos.weather.R;
import org.zackratos.weather.hewind.Hourly;
import org.zackratos.weather.hewind.Wind;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/18.
 */

public class HourDialog extends DialogFragment {


    private static final String HOUR = "hourly";


    @BindView(R.id.hour_txt)
    TextView txtView;

    @BindView(R.id.hour_hum)
    TextView humView;

    @BindView(R.id.hour_pop)
    TextView popView;

    @BindView(R.id.hour_pres)
    TextView presview;

    @BindView(R.id.hour_tmp)
    TextView tmpView;

    @BindView(R.id.hour_deg)
    TextView degView;

    @BindView(R.id.hour_dir)
    TextView dirView;

    @BindView(R.id.hour_sc)
    TextView scView;

    @BindView(R.id.hour_spd)
    TextView spdView;

    Unbinder unbinder;

    public static HourDialog newInstance(Hourly hourly) {

        HourDialog dialog = new HourDialog();

        Bundle args = new Bundle();

        args.putParcelable(HOUR, hourly);

        dialog.setArguments(args);

        return dialog;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Hourly hourly = getArguments().getParcelable(HOUR);

        Wind wind = hourly.getWind();

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_hour, null);

        unbinder = ButterKnife.bind(this, view);

        txtView.setText(hourly.getCond().getTxt());

        humView.setText(String.format("%s %%", hourly.getHum()));

        popView.setText(String.format("%s %%", hourly.getPop()));

        presview.setText(String.format("%s pa", hourly.getPres()));

        tmpView.setText(String.format("%s°C", hourly.getTmp()));

        degView.setText(String.format("%s° / 360 °", wind.getDeg()));

        dirView.setText(wind.getDir());

        scView.setText(wind.getSc());

        spdView.setText(String.format("%s kmph", wind.getSpd()));

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(hourly.getDate())
                .setView(view)
                .create();

        return dialog;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

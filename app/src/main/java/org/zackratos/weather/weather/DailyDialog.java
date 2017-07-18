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
import org.zackratos.weather.hewind.Daily;
import org.zackratos.weather.hewind.Wind;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/2.
 */

public class DailyDialog extends DialogFragment {


    private static final String DAILY = "daily";
    @BindView(R.id.daily_mr)
    TextView mrView;
    @BindView(R.id.daily_ms)
    TextView msView;
    @BindView(R.id.daily_sr)
    TextView srView;
    @BindView(R.id.daily_ss)
    TextView ssView;
    @BindView(R.id.daily_txt_d)
    TextView txtDView;
    @BindView(R.id.daily_txt_n)
    TextView txtNView;
    @BindView(R.id.daily_hum)
    TextView humView;
    @BindView(R.id.daily_pcpn)
    TextView pcpnView;
    @BindView(R.id.daily_pop)
    TextView popView;
    @BindView(R.id.daily_pres)
    TextView presView;
    @BindView(R.id.daily_max)
    TextView maxView;
    @BindView(R.id.daily_min)
    TextView minView;
    @BindView(R.id.daily_deg)
    TextView degView;
    @BindView(R.id.daily_dir)
    TextView dirView;
    @BindView(R.id.daily_sc)
    TextView scView;
    @BindView(R.id.daily_spd)
    TextView spdView;

    public static DailyDialog newInstance(Daily daily) {


        DailyDialog dialog = new DailyDialog();
        Bundle args = new Bundle();
        args.putParcelable(DAILY, daily);
        dialog.setArguments(args);
        return dialog;
    }


    Unbinder unbinder;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Daily daily = getArguments().getParcelable(DAILY);

        Daily.Astro astro = daily.getAstro();
        Daily.Cond cond = daily.getCond();
        Daily.Tmp tmp = daily.getTmp();
        Wind wind = daily.getWind();


        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_daily, null);

        unbinder = ButterKnife.bind(this, view);

        mrView.setText(astro.getMr());
        srView.setText(astro.getSr());
        msView.setText(astro.getMs());
        ssView.setText(astro.getSs());
        txtDView.setText(cond.getTxt_d());
        txtNView.setText(cond.getTxt_n());
        humView.setText(daily.getHum());
        pcpnView.setText(daily.getPcpn());
        popView.setText(daily.getPop());
        presView.setText(daily.getPres());
        maxView.setText(tmp.getMax());
        minView.setText(tmp.getMin());
        degView.setText(wind.getDeg());
        dirView.setText(wind.getDir());
        scView.setText(wind.getSc());
        spdView.setText(wind.getSpd());

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(daily.getDate())
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

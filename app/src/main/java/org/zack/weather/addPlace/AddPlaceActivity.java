package org.zack.weather.addPlace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.melnykov.fab.FloatingActionButton;

import org.zack.library.UltimateBar;
import org.zack.weather.BaseActivity;
import org.zack.weather.R;
import org.zack.weather.addPlace.province.ProvinceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlaceActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, AddPlaceActivity.class);
    }



//    @BindView(R.id.add_place_locate)
    private FloatingActionButton locateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.main));
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

        setSupportActionBar(toolbar);

        locateButton = ButterKnife.findById(this, R.id.add_place_locate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, ProvinceFragment.newInstance())
                .commit();
    }




    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    public FloatingActionButton getLocateButton() {
        return locateButton;
    }


}

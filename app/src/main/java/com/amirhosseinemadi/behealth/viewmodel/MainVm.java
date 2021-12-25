package com.amirhosseinemadi.behealth.viewmodel;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;

import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.view.ChangeFragment;

public class MainVm extends BaseObservable {

    SharedPreferences sharedPreferences;
    private AppCompatActivity activity;

    public MainVm(AppCompatActivity activity)
    {

        this.activity = activity;

    }


    public void changeOnClick()
    {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new ChangeFragment()).commit();
    }


    public void settingsOnClick()
    {

    }

}

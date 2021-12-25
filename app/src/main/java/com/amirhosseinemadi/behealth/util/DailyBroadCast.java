package com.amirhosseinemadi.behealth.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;

public class DailyBroadCast extends BroadcastReceiver {


    //TODO add record to database.

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PrefManager prefManager = Application.dComponent.prefManager();

        float previousStep = prefManager.getPreviousStep()+prefManager.getStep();
        prefManager.setPreviousStep(previousStep).setBackupStep(0);
    }

}

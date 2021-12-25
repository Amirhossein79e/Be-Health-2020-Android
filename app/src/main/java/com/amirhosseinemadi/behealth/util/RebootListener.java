package com.amirhosseinemadi.behealth.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import java.util.Calendar;

public class RebootListener extends BroadcastReceiver {

    private PrefManager prefManager;
    
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            prefManager = Application.dComponent.prefManager();
            updateRebootState();
            restartAlarm();
        }

    }


    private void updateRebootState()
    {
        prefManager.setReboot(true);
    }


    private void restartAlarm()
    {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if ((StepService.isRunning == null) && (stepCounter != null && stepDetector != null) && Application.dComponent.prefManager().getHeight() != 0)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
                {
                    Application.dComponent.prefManager()
                            .setPreviousStep(0)
                            .setReboot(true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        context.startForegroundService(new Intent(context, StepService.class));
                    } else
                    {
                        context.startService(new Intent(context, StepService.class));
                    }

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent stepBroadcastIntent = new Intent(context, DailyBroadCast.class);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 57);
                    PendingIntent stepPending = PendingIntent.getBroadcast(context, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
                }
            }else
            {
                if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
                {
                    Application.dComponent.prefManager()
                            .setPreviousStep(0)
                            .setReboot(true);

                    context.startService(new Intent(context, StepService.class));

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent stepBroadcastIntent = new Intent(context, DailyBroadCast.class);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 57);
                    PendingIntent stepPending = PendingIntent.getBroadcast(context, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
                }
            }


        }
    }

}

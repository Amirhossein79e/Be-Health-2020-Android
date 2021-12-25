package com.amirhosseinemadi.behealth.view;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.callback.DialogVmCallback;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.databinding.ActivityMainBinding;
import com.amirhosseinemadi.behealth.databinding.DetailDialogBinding;
import com.amirhosseinemadi.behealth.util.DailyBroadCast;
import com.amirhosseinemadi.behealth.util.StepService;
import com.amirhosseinemadi.behealth.viewmodel.DialogVm;
import com.amirhosseinemadi.behealth.viewmodel.MainVm;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DialogVmCallback {

    /*
    - Step count in stepFragment
    - walking time calculator
    - Stride
    - User Information
     */

    private PrefManager prefManager;
    private AlarmManager alarmManager;
    private Intent stepBroadcastIntent;
    private PendingIntent stepPending;
    private Dialog dialog;
    private Calendar calendar;
    private ActivityMainBinding binding;
    private Sensor STEP_COUNTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainVm viewModel = new MainVm(this);
        binding.setViewModel(viewModel);

        prefManager = Application.dComponent.prefManager();

        binding.bottomNavigationMain.setSelectedItemId(R.id.step_menu_item);
        binding.bottomNavigationMain.setOnNavigationItemSelectedListener(this::onBottomNavMainItemSelected);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        STEP_COUNTER = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (STEP_COUNTER != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                if (StepService.isRunning == null && !prefManager.getFirst() && prefManager.getHeight() != 0 &&  checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(this, StepService.class);
                    startForegroundService(intent);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    stepBroadcastIntent = new Intent(this, DailyBroadCast.class);
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 57);
                    stepPending = PendingIntent.getBroadcast(this, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
                }
            }else
            {
                if (StepService.isRunning == null && !prefManager.getFirst() && prefManager.getHeight() != 0)
                {
                    Intent intent = new Intent(this, StepService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    stepBroadcastIntent = new Intent(this, DailyBroadCast.class);
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 57);
                    stepPending = PendingIntent.getBroadcast(this, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
                }
            }
        }


        if (prefManager.getHeight()==0 && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !prefManager.getFirst())
        {
            dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.layout_radius));
            DetailDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.detail_dialog,null,false);
            DialogVm dialogVm = new DialogVm(MainActivity.this);
            dialogBinding.setViewModel(dialogVm);
            dialog.setContentView(dialogBinding.getRoot());
            dialog.show();
        }


        if (!prefManager.getFirst() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && prefManager.getHeight() == 0)
        {
            dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.layout_radius));
            DetailDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.detail_dialog,null,false);
            DialogVm dialogVm = new DialogVm(MainActivity.this);
            dialogBinding.setViewModel(dialogVm);
            dialog.setContentView(dialogBinding.getRoot());
            dialog.show();
        }


        if (STEP_COUNTER != null)
        {
            //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                if (!(checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 5);
                }
            }else
            {
                if (prefManager.getFirst())
                {
                    dialog = new Dialog(MainActivity.this);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.layout_radius));
                    DetailDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.detail_dialog,null,false);
                    DialogVm dialogVm = new DialogVm(MainActivity.this);
                    dialogBinding.setViewModel(dialogVm);
                    dialog.setContentView(dialogBinding.getRoot());
                    dialog.show();
                    prefManager.setFirst(false);
                }
            }
        }else
        {
            Snackbar.make(findViewById(R.id.layout_main),"Required Sensor not supported on your device.",Snackbar.LENGTH_LONG).setAnchorView(R.id.bottom_navigation_main).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!prefManager.getFirst())
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new StepFragment()).commit();
        }


    }

    private boolean onBottomNavMainItemSelected(MenuItem item)
    {

        item.setChecked(true);

        switch (item.getItemId())
        {

            case R.id.step_menu_item :
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new StepFragment()).commit();
                break;

            case R.id.heart_menu_item :
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new StatusFragment()).commit();
                break;

            case R.id.calories_menu_item :
                //TODO Calories Fragment
                break;

        }

        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length != 0)
        {

        switch (requestCode)
        {
            case 5:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    prefManager.setFirst(false);
                    dialog = new Dialog(MainActivity.this);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.layout_radius));
                    DetailDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.detail_dialog,null,false);
                    DialogVm dialogVm = new DialogVm(MainActivity.this);
                    dialogBinding.setViewModel(dialogVm);
                    dialog.setContentView(dialogBinding.getRoot());
                    dialog.show();
                }else
                if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                    if (prefManager.getFirst())
                    {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                    dialog = new Dialog(MainActivity.this);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.layout_radius));
                    DetailDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.detail_dialog,null,false);
                    DialogVm dialogVm = new DialogVm(MainActivity.this);
                    dialogBinding.setViewModel(dialogVm);
                    dialog.setContentView(dialogBinding.getRoot());
                    dialog.show();
                    }
                    prefManager.setFirst(false);
                }
                break;
        }
        }

    }

    @Override
    public void onIncorrect() {

        Toast.makeText(MainActivity.this, "Detail is incorrect", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCorrect() {

        if (STEP_COUNTER != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(this, StepService.class);
                    startForegroundService(intent);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    stepBroadcastIntent = new Intent(this, DailyBroadCast.class);
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 57);
                    stepPending = PendingIntent.getBroadcast(this, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
                }else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Permission denied");
                    alertDialog.setMessage("Permission denied. Be Health application need Physical Activity permission for record your walking info." +
                            "if you want allow permission go to Settings->Apps->Be-Health->Permissions->Physical activity and allow the permission.");
                    alertDialog.setIcon(R.drawable.ic_step);
                    alertDialog.setPositiveButton("OK",null);
                    alertDialog.show();
                }
            }else
            {
                Intent intent = new Intent(this, StepService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                stepBroadcastIntent = new Intent(this, DailyBroadCast.class);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 57);
                stepPending = PendingIntent.getBroadcast(this, 0, stepBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stepPending);
            }
        }

    }

    @Override
    public void onCanceled() {

        dialog.dismiss();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new StepFragment()).commit();

    }
}
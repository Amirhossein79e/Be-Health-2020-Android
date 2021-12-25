package com.amirhosseinemadi.behealth.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;
import com.amirhosseinemadi.behealth.R;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.DComponent;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.model.database.Dao;
import com.amirhosseinemadi.behealth.model.entity.GeneralModel;
import com.amirhosseinemadi.behealth.view.MainActivity;
import com.amirhosseinemadi.behealth.view.StepFragment;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StepService extends Service {

    private SensorManager sensorManager;
    private Sensor stepCounter;
    public static MutableLiveData<Integer> timeLiveData = new MutableLiveData<>();
    public static MutableLiveData<Integer> stepLiveData = new MutableLiveData<>();
    public static Integer isRunning = null;
    private Dao dao;
    private PrefManager prefManager;
    private SimpleDateFormat dateFormat;
    private GeneralModel model;
    private boolean isDay;
    private int counter;

    @Override
    public void onCreate() {
        super.onCreate();

        PendingIntent pendingIntent = PendingIntent.getService(this,1,new Intent(this, MainActivity.class),PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder notification;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("2329","Walking Channel",NotificationManager.IMPORTANCE_LOW);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.createNotificationChannel(channel);
            notification = new Notification.Builder(this,"2329");
        }else
        {
            notification = new Notification.Builder(this);
        }
        notification.setPriority(Notification.PRIORITY_LOW);
        notification.setContent(new RemoteViews(getPackageName(), R.layout.notification_layout));
        notification.setSmallIcon(R.drawable.ic_calories);
        notification.setContentIntent(pendingIntent);
        startForeground(2329, notification.build());

        dao = Application.dComponent.dao();
        isDay = false;
        dateFormat = new SimpleDateFormat("yyMMdd");
        prefManager = Application.dComponent.prefManager();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        isRunning = 0;

        goAsync().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Float>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Float aFloat) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return START_STICKY;
    }


    private Observable<Float> goAsync()
    {
        Observable<Float> observable = Observable.generate(it->
        {
            stepCounter();
        });

        return observable;
    }


    private void stepCounter()
    {
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                isDay = model != null && model.getId() == Integer.parseInt(dateFormat.format(new Date(event.timestamp)));

                if (!isDay)
                {
                    List<GeneralModel> list = dao.selectByIdSync(Integer.parseInt(dateFormat.format(new Date(event.timestamp))));

                    if (list.size() > 0)
                    {
                        model = list.get(0);

                        if (!prefManager.getReboot())
                        {
                            prefManager.setBackupStep(model.getStep());
                        }

                    }else
                    {
                        prefManager.setBackupStep(0);
                        prefManager.setPreviousStep(event.values[0]);
                        model = new GeneralModel.Builder().setId(Integer.parseInt(dateFormat.format(new Date(event.timestamp)))).build();
                        model.setDate(event.timestamp);
                        model.setStep((int) (event.values[0] - prefManager.getPreviousStep()));
                        dao.insertGeneral(model);
                    }

                }else if (counter >= 20)
                {
                    if (prefManager.getReboot())
                    {
                        prefManager.setBackupStep(model.getStep());
                        prefManager.setReboot(false);
                        model.setStep(model.getStep() + (int) (event.values[0] - prefManager.getPreviousStep()));
                    }else
                    {
                        model.setStep((int) (prefManager.getBackupStep() + (int) (event.values[0] - prefManager.getPreviousStep())));
                    }

                    dao.updateGeneral(model);
                    counter = 0;

                }else
                {
                    counter ++;
                }

                stepLiveData.postValue((int) (prefManager.getBackupStep() + (event.values[0] - prefManager.getPreviousStep())));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }
        },stepCounter,SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}

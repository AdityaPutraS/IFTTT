package com.ksatukeltiga.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class GyroscopeModule extends ConditionModule implements SensorEventListener {
    private double threshold;
    private double value;
    private boolean repeated;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Bundle bundle;
    private String actionClass;
    private Object object;

    public GyroscopeModule(double threshold, boolean repeated, Context context) {
        this.moduleName = "GyroscopeModule";
        this.threshold = threshold;
        this.value = 0;
        this.repeated = repeated;
        this.context = context;
        this.conditionString = "Gyroscope value > " + threshold;
        this.data = "" + threshold;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void connectAksi(ActionModule aksi) {
        Log.println(Log.INFO, "GyroscopeModule", aksi.getClass().getName());
        Intent intent = new Intent(context, aksi.getClass());
        intent.putExtras(aksi.getBundle());
        Log.println(Log.INFO, "GyroscopeModule", aksi.getBundle().toString());
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float ax, ay, az;

        ax = event.values[0];
        ay = event.values[1];
        az = event.values[2];

        this.value = Math.sqrt((ax * ax) + (ay * ay) + (az * az));
        if (this.value > this.threshold) {
            // trigger action
            Class action = null;
            try {
                action = Class.forName(this.actionClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Log.println(Log.INFO, "GyroscopeModule", this.actionClass);
            Intent intent = new Intent(context, action);
            intent.putExtras(this.bundle);
            Log.println(Log.INFO, "GyroscopeModule", this.bundle.toString());
            Log.println(Log.INFO, "Gyroscope value", ""+this.value);
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmIntent = PendingIntent.getService(context, 0, intent, 0);
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(), alarmIntent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

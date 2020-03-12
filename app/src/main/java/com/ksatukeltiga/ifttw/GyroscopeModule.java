package com.ksatukeltiga.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class GyroscopeModule extends ConditionModule implements SensorEventListener {
    private double threshold;
    private double value;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

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
    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void connectAksi(ActionModule aksi) {
        if (this.value > this.threshold) {
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Log.println(Log.INFO, "GyroscopeModule", aksi.getClass().getName());
            Intent intent = new Intent(context, aksi.getClass());
            intent.putExtras(aksi.getBundle());
            Log.println(Log.INFO, "GyroscopeModule", aksi.getBundle().toString());
            alarmIntent = PendingIntent.getService(context, 0, intent, 0);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            2 * 1000, alarmIntent);
            Log.println(Log.INFO, "GyroscopeModule", "done create alarm");
        } else {
            Log.println(Log.INFO, "GyroscopeModule", "don't create alarm");
        }
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
            Log.println(Log.INFO, "Gyroscope value", ""+this.value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.ksatukeltiga.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class TimerModule extends ConditionModule {
    private Date activateWhen;
    private Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private int requestCode;

    public TimerModule(Date activate, boolean repeated, Context context)
    {
        this.moduleName = "TimerModule";
        this.activateWhen = activate;
        Random rand = new Random();
        this.requestCode = rand.nextInt();
        this.data = this.requestCode + "---" + activate.getTime();
        this.repeated = repeated;
        this.conditionString = activate.toString();
        this.context = context;
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        String[] args = data.split("---");
        this.requestCode = Integer.parseInt(args[0]);
        this.activateWhen = new Date(Long.parseLong(args[1]));
    }

    @Override
    public void connectAksi(ActionModule aksi)
    {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.println(Log.INFO, "TimerModule", aksi.getClass().getName());
        Intent intent = new Intent(context, aksi.getClass());
        intent.putExtras(aksi.getBundle());
        Log.println(Log.INFO, "TimerModule", aksi.getBundle().toString());
        alarmIntent = PendingIntent.getService(context, this.requestCode, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        2 * 1000, alarmIntent);
        Log.println(Log.INFO, "TimerModule", "done create alarm");
    }
}

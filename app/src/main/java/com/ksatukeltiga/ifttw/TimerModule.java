package com.ksatukeltiga.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class TimerModule extends ConditionModule {
    private Date activate_when;
    private Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public TimerModule(Date activate, boolean repeated, Context context)
    {
        this.moduleName = "TimerModule";
        this.activate_when = activate;
        this.data = Long.toString(activate.getTime());
        this.repeated = repeated;
        this.conditionString = activate.toString();
        this.context = context;
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        this.activate_when = new Date(Long.valueOf(data));
    }

    @Override
    public void connectAksi(ActionModule aksi)
    {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.println(Log.INFO, "TimerModule", aksi.getClass().getName());
        Intent intent = new Intent(context, aksi.getClass());
        intent.putExtras(aksi.getBundle());
        Log.println(Log.INFO, "TimerModule", aksi.getBundle().toString());
        alarmIntent = PendingIntent.getService(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        2 * 1000, alarmIntent);
        Log.println(Log.INFO, "TimerModule", "done create alarm");
    }
}

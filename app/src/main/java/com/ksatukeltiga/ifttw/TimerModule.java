package com.ksatukeltiga.ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TimerModule extends ConditionModule {
    private final String[] dayArr = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private Date activateWhen;
    private Context context;
    protected boolean[] repeated;
    private int requestCode;
    private boolean repeatEveryDay, isRepeating;

    public TimerModule(Date activate, boolean[] repeated, Context context)
    {
        Log.println(Log.INFO, "TimerModule", "CTOR TimerModule");
        this.moduleName = "TimerModule";
        this.activateWhen = (Date) activate.clone();
        Random rand = new Random();
        this.requestCode = rand.nextInt();
        this.repeated = repeated.clone();
        this.data = this.requestCode + "---" + activate.getTime() + "---";

        for (int i = 0; i < this.repeated.length; i++) {
            if(this.repeated[i])
            {
                this.data += "1";
            }else{
                this.data += "0";
            }
        }
        checkRepeated();
        updateConditionString();

        this.context = context;
    }

    @Override
    public void updateConditionString(){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("EEE, MMM d yyyy, KK:mm aa");
        this.conditionString = dateTimeFormat.format(this.activateWhen);
    }

    public void checkRepeated()
    {
        this.repeatEveryDay = true;
        this.isRepeating = false;
        for (int i = 0; i < this.repeated.length; i++) {
            if(this.repeated[i])
            {
                this.isRepeating = true;
            }else{
                this.repeatEveryDay = false;
            }
        }

        if(this.isRepeating)
        {
            this.conditionString += "\nRepeat  ";
        }
        if(this.repeatEveryDay)
        {
            this.conditionString += "Everyday  ";
        }else{
            for (int i = 0; i < this.repeated.length; i++) {
                this.conditionString += this.repeated[i] ? dayArr[i] : "" ;
                if(this.repeated[i])
                {
                    this.conditionString += ", ";
                }
            }
            this.conditionString = this.conditionString.substring(0, this.conditionString.length() - 2);
        }
    }

    @Override
    public void setData(String data)
    {
        Log.println(Log.INFO, "TimerModule", "Set Data TimerModule");
        this.data = data;
        String[] args = data.split("---");
        this.requestCode = Integer.parseInt(args[0]);
        this.activateWhen = new Date(Long.parseLong(args[1]));
        this.repeated = new boolean[7];
        for(int i = 0; i < args[2].length(); i++)
        {
            this.repeated[i] = args[2].charAt(i) == '1';
        }
        checkRepeated();
        updateConditionString();
    }

    @Override
    public void connectAksi(ActionModule aksi)
    {
        Log.println(Log.INFO, "TimerModule", "ConnectAksi data: " + this.data);
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.println(Log.INFO, "TimerModule", "ConnectAksi className: " + aksi.getClass().getName());
        Intent intent = new Intent(context, aksi.getClass());
        intent.putExtras(aksi.getBundle());
        Log.println(Log.INFO, "TimerModule", "ConnectAksi bundle: " + aksi.getBundle().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.activateWhen);
        // Cek apakah activateWhen sudah lewat / belum
        long diff = Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();
        if(diff <= 1000 * 60) {
            if (this.repeatEveryDay) {
                Log.println(Log.INFO, "TimerModule", "Everyday " + calendar.getTime().toString() + " " + this.requestCode);
                PendingIntent alarmIntent = PendingIntent.getService(context, this.requestCode, intent, 0);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            } else if (this.isRepeating) {
                int[] dayArr = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
                        Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY,
                        Calendar.SATURDAY};
                for (int i = 0; i < this.repeated.length; i++) {
                    if (this.repeated[i]) {
                        //Bentuk tanggal untuk hari ke - i
                        calendar.set(Calendar.DAY_OF_WEEK, dayArr[i]);
                        PendingIntent alarmIntent = PendingIntent.getService(context, this.requestCode + i,
                                intent, 0);
                        Log.println(Log.INFO, "TimerModule", "Repeating " + calendar.getTime().toString() + " " + this.requestCode);
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    }
                }
            } else {
                //Alarm biasa, nyala sekali saja
                Log.println(Log.INFO, "TimerModule", "Once " + calendar.getTime().toString() + " " + this.requestCode);
                PendingIntent alarmIntent = PendingIntent.getService(context, this.requestCode, intent, 0);
                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
            }
            Log.println(Log.INFO, "TimerModule", "ConnectAksi done create alarm");
        }else{
            Log.println(Log.INFO, "TimerModule", "ConnectAksi batal create alarm karena sudah lewat");
        }
    }

    @Override
    public void cancelAksi(ActionModule aksi)
    {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Buat matching intent dengan sebelumnya
        Intent intent = new Intent(context, aksi.getClass());
        intent.putExtras(aksi.getBundle());
        Log.println(Log.INFO, "TimerModule", "Alarm " + this.requestCode + " dibatalkan");
        PendingIntent alarmIntent = PendingIntent.getService(context, this.requestCode, intent, 0);
        alarmMgr.cancel(alarmIntent);
        for (int i = 0; i < this.repeated.length; i++) {
            if(this.repeated[i] || (i == 0)) {
                Log.println(Log.INFO, "TimerModule", "Alarm " + this.requestCode+i + " dibatalkan");
                alarmIntent = PendingIntent.getService(context, this.requestCode + i, intent, 0);
                alarmMgr.cancel(alarmIntent);
            }
        }
    }
}

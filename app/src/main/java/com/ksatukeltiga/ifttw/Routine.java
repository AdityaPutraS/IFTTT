package com.ksatukeltiga.ifttw;

import android.content.Context;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Routine implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded(prefix = "kondisi_")
    private ConditionModule kondisi;
    @Embedded(prefix = "aksi_")
    private ActionModule aksi;

    private boolean active;

    public Routine(ConditionModule kondisi, ActionModule aksi) {
        try {
            this.kondisi = (ConditionModule) kondisi.clone();
            //        this.kondisi.setData(kondisi.getData());
            Log.println(Log.INFO, "Routine", "Routine kondisi : " + kondisi.getData());
            this.aksi = (ActionModule) aksi.clone();
            //        this.aksi.setData(aksi.getData());
            Log.println(Log.INFO, "Routine", "Routine aksi : " + aksi.getData());
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        this.setActive(true);
    }

    public void initRoutine(Context context)
    {
        Log.println(Log.INFO, "Routine", "InitRoutine bkondisi : " + this.kondisi.getData());
        Log.println(Log.INFO, "Routine", "InitRoutine baksi : " + this.aksi.getData());
        if(this.kondisi.getModuleName().equalsIgnoreCase("TimerModule"))
        {
            ConditionModule temp = new TimerModule(new Date(), new boolean[7], context);
            temp.setData(this.kondisi.getData());
            this.kondisi = temp;
        }
        if(this.aksi.getModuleName().equalsIgnoreCase("NotifyModule"))
        {
            ActionModule temp = new NotifyModule("", "");
            temp.setData(this.aksi.getData());
            this.aksi = temp;
        }
        Log.println(Log.INFO, "Routine", "InitRoutine akondisi : " + this.kondisi.getData());
        Log.println(Log.INFO, "Routine", "InitRoutine aaksi : " + this.aksi.getData());
    }

    public ConditionModule getKondisi() {
        return kondisi;
    }

    public void setKondisi(ConditionModule kondisi) {
        this.kondisi = kondisi;
    }

    public ActionModule getAksi() {
        return aksi;
    }

    public void setAksi(ActionModule aksi) {
        this.aksi = aksi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active){
            this.kondisi.connectAksi(this.aksi);
        }else{
            this.kondisi.cancelAksi(this.aksi);
        }
    }
}

package com.ksatukeltiga.ifttw;

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
}

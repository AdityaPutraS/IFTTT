package com.ksatukeltiga.ifttw.actionmodule;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ActionModule extends IntentService implements Serializable, Cloneable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    protected String data;
    protected String moduleName;
    protected String actionString;

    public ActionModule(int id, String data, String moduleName, String actionString) {
        super(moduleName);
        this.data = data;
        this.moduleName = moduleName;
        this.actionString = actionString;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }

    public void updateActionString() {}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        updateActionString();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getActionString() {
        return actionString;
    }

    public void setActionString(String actionString) {
        this.actionString = actionString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected void onHandleIntent(Intent workIntent){
        Log.println(Log.INFO, "ActionModule", "Masuk handle intent action module");
    }

    public Bundle getBundle() {
        return new Bundle();
    }
}

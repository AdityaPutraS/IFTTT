package com.ksatukeltiga.ifttw.conditionmodule;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ksatukeltiga.ifttw.actionmodule.ActionModule;

import java.io.Serializable;

@Entity
public class ConditionModule implements Serializable, Cloneable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    protected String data;
    protected String moduleName;
    protected String conditionString;

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }
    public void updateConditionString() {}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        updateConditionString();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getConditionString() {
        return conditionString;
    }

    public void setConditionString(String conditionString) {
        this.conditionString = conditionString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void connectAksi(ActionModule aksi) {}

    public void cancelAksi(ActionModule aksi) {}
}

package com.ksatukeltiga.ifttw;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ConditionModule implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    protected String data;
    protected String moduleName;
    protected boolean repeated;
    protected String conditionString;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void connectAksi(ActionModule aksi) {}
}

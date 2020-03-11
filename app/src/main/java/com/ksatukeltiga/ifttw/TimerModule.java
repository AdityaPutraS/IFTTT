package com.ksatukeltiga.ifttw;

import java.util.Date;

public class TimerModule extends ConditionModule {
    private Date activate_when;
    public TimerModule(Date activate, boolean repeated)
    {
        this.moduleName = "TimerModule";
        this.activate_when = activate;
        this.data = Long.toString(activate.getTime());
        this.repeated = repeated;
        this.conditionString = activate.toString();
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        this.activate_when = new Date(Long.valueOf(data));
    }
}

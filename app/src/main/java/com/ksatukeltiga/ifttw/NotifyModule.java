package com.ksatukeltiga.ifttw;

public class NotifyModule extends ActionModule {
    private String notification;

    public NotifyModule(String notification)
    {
        this.moduleName = "NotifyModule";
        this.data = notification;
        this.notification = notification;
        this.actionString = "Notify \"" + notification + "\"";
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        this.notification = data;
    }
}

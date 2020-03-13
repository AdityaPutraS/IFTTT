package com.ksatukeltiga.ifttw.actionmodule;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WifiModule extends ActionModule {

    public WifiModule () {
        super(0, "", "WifiModule", "");
    }

    public WifiModule (String commandOnOff) {
        super(0, commandOnOff, "WifiModule", "Turn "+commandOnOff + " WiFi");
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("action", "WiFi");
        bundle.putString("command", this.data);
        return bundle;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String command = workIntent.getStringExtra("command");
        boolean enable = false;
        if (command.equals("on")) {
            enable = true;
        } else if (command.equals("off")) {
            enable = false;
        }
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        boolean statusWifi = wifiManager.isWifiEnabled();
        Log.i("wifi-status", String.valueOf(statusWifi));
        Log.i("wifi-command", String.valueOf(enable));
        if (statusWifi != enable) {
            wifiManager.setWifiEnabled(enable);
        } else {
            Toast.makeText(this, "WiFi " + (statusWifi?"Enabled":"Disabled"), Toast.LENGTH_LONG).show();
        }
    }
}

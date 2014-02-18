package com.androidtablet.wifidirectapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.widget.Toast;

public class WiFiBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager manager;
    private Channel channel;
   private WiFiDirectAppActivity activity;

    public WiFiBroadcastReceiver(WifiP2pManager manager, Channel channel, WiFiDirectAppActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
             int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
             if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) 
                Toast.makeText(context, "Wi-Fi Direct is enable", Toast.LENGTH_LONG).show();
             else 
                Toast.makeText(context, "Wi-Fi Direct is not enable", Toast.LENGTH_LONG).show();
             }          
    }
}
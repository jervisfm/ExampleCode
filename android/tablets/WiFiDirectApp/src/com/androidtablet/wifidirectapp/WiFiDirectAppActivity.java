package com.androidtablet.wifidirectapp;

import android.os.Bundle;
import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;

public class WiFiDirectAppActivity extends Activity {
    WifiP2pManager wifiP2pManager;
    Channel channel;
    BroadcastReceiver bcReceiver;
    IntentFilter intentFilter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wi_fi_direct_app);
        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel =  (Channel) wifiP2pManager.initialize(this, getMainLooper(), null); 
        bcReceiver = new WiFiBroadcastReceiver(wifiP2pManager, channel, this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
       /* intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);*/
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                Toast.makeText(WiFiDirectAppActivity.this, "Wi-FI Direct is Enabled", Toast.LENGTH_LONG).show();  
            }
            public void onFailure(int reasonCode) {
           	String errorMessage = "Wi-Fi Direct Failed.";
            	switch (reasonCode) {
            	case WifiP2pManager.BUSY :
            	errorMessage += " Framework is busy."; break;
            	case WifiP2pManager.ERROR :
            	errorMessage += " Some internal error occurred."; break;
            	case WifiP2pManager.P2P_UNSUPPORTED :
            	errorMessage += " Unsupported."; break;
            	default:
            	errorMessage += " Unknown error occurred."; break;
            	}
                Toast.makeText(WiFiDirectAppActivity.this, errorMessage, Toast.LENGTH_LONG).show();  
            }
        });	
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bcReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bcReceiver);
    }   
}

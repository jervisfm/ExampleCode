package com.androidtablet.wifiapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.widget.TextView;
import android.content.IntentFilter;

public class WiFiAppActivity extends Activity {
	private WifiManager wifiManager;
	TextView wifiStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wi_fi_app);
	     wifiStatus = (TextView)findViewById(R.id.wifistatus);
	     wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
	     this.registerReceiver(this.WifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_wi_fi_app, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enable_wifi_btn:         
                wifiManager.setWifiEnabled(true);
                break;
            case R.id.disable_wifi_btn: 
                wifiManager.setWifiEnabled(false);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private BroadcastReceiver WifiStateChangedReceiver = new BroadcastReceiver(){
   @Override
   public void onReceive(Context context, Intent intent) {  
    int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);   
    switch(extraWifiState){
    case WifiManager.WIFI_STATE_DISABLED:
     wifiStatus.setText("Wi-Fi Disabled");
     break;
    case WifiManager.WIFI_STATE_DISABLING:
     wifiStatus.setText("Wi-Fi Disabling");
     break;
    case WifiManager.WIFI_STATE_ENABLED:
     wifiStatus.setText("Wi-Fi Enabled");
     break;
    case WifiManager.WIFI_STATE_ENABLING:
     wifiStatus.setText("Wi-Fi Enabling");
     break;
    case WifiManager.WIFI_STATE_UNKNOWN:
     wifiStatus.setText("Unknown WiFi Status");
     break;
    }    
   }
   };    
}

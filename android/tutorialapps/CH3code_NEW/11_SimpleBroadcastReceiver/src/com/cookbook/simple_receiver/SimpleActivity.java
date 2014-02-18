package com.cookbook.simple_receiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class SimpleActivity extends Activity {
    SimpleBroadcastReceiver intentReceiver = 
        new SimpleBroadcastReceiver();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        IntentFilter intentFilter = 
            new IntentFilter(Intent.ACTION_CAMERA_BUTTON);
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        registerReceiver(intentReceiver, intentFilter); 

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(intentReceiver);
        super.onDestroy();
    }   
}
package com.androidtablet.broadcastingintent;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class ReceiveBroadcastActivity extends BroadcastReceiver {
    @Override 
    public void onReceive(Context context, Intent intent) { 
        String actionName = intent.getAction();
        if(actionName != null && actionName.equals("com.androidtablet.broadcastintent")) {
            String msg = intent.getStringExtra("message");
           Log.d("Text Received from Broadcast Intent: ", msg);
        }
    } 
}

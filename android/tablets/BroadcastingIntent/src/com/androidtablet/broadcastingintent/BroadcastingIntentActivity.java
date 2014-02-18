package com.androidtablet.broadcastingintent;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import android.util.Log;

public class BroadcastingIntentActivity extends Activity {
    public static String BROADCAST_STRING =  "com.androidtablet.broadcastintent";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcasting_intent);
        Button broadcastButton = (Button) this.findViewById(R.id.broadcast_button);
        broadcastButton.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v) {
                Intent broadcastIntent = new Intent();
                broadcastIntent.putExtra("message", "New Message arrived");
                broadcastIntent.setAction(BROADCAST_STRING); 
                sendBroadcast(broadcastIntent);
            }
        });
    }

    private BroadcastReceiver myBroadcastReceiver =   new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { 
            String actionName = intent.getAction();
            if(actionName != null && actionName.equals("com.androidtablet.broadcastintent")) {
                String msg = intent.getStringExtra("message");
                Log.d("Text Received from Broadcast Intent: ", msg); 
            }
        }
    };

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();  
        intentFilter.addAction("com.androidtablet.broadcastintent");  
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }  
}


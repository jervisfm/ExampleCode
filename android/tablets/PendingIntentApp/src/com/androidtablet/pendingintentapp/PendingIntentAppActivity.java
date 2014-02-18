package com.androidtablet.pendingintentapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.Notification;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

public class PendingIntentAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_intent_app);
        Button createButton = (Button) findViewById(R.id.createbutton);
        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendingIntentAppActivity. this, WelcomeActivity.class);
                PendingIntent pendIntent = PendingIntent.getActivity(PendingIntentAppActivity.this,0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
                Notification notification = new Notification();
                Notification.Builder builder = new Notification.Builder(PendingIntentAppActivity.this)
                .setSmallIcon(R.drawable.glowingbulb)
                .setAutoCancel(true)
                .setTicker("Notification to launch Pending Intent")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Message")
                .setContentText("Let us launch the pending Intent")
                .setContentIntent(pendIntent);   
                notification = builder.build();
                notificationManager.notify(0, notification);   
            }
        });
    }
}

package com.cookbook.simple_service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class SimpleIntentService extends IntentService {

	Notification notification=null;
	private NotificationManager mNManager;
	
	public SimpleIntentService() {
		super("SimpleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		createNotification();
		if(intent.hasExtra("msg")){
			updateNotification(intent.getStringExtra("msg"));
		     mNManager.notify(1, notification);
		}
		
	}


    protected void createNotification(){
    	if(mNManager==null){
    		mNManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	}
        notification = new Notification(
        		R.drawable.icon,
                "New event of importance",
                System.currentTimeMillis());     	
        // Set the icon, scrolling text and timestamp

   
    }
    
    protected void updateNotification(final String text){
        // The PendingIntent to launch our activity if the user selects this notification
        Intent startIntent=new Intent(getApplicationContext(),SimpleActivity.class);
        //startIntent.setFlags(Intent.FLA)
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,startIntent, 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(
        		this, 
        		"Message received",
        		text,
                contentIntent
        );
        
    }	
	
}

package com.cookbook.show_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ShowNotification extends Activity { 

    private NotificationManager mNManager; 
    private static final int NOTIFY_ID=1100; 

    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 

        String ns = Context.NOTIFICATION_SERVICE;
        mNManager = (NotificationManager) getSystemService(ns); 

        Button startSimple = (Button)findViewById(R.id.startSimple); 
        Button stopSimple = (Button)findViewById(R.id.stopSimple); 

        startSimple.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                final Notification msg = new Notification(R.drawable.icon,
                        "New event of importance",
                        System.currentTimeMillis()); 

            	Context context = getApplicationContext(); 
                CharSequence contentTitle = "ShowNotification Example"; 
                CharSequence contentText = "Browse Android Cookbook Site"; 
                Intent msgIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.pearson.com")); 
                PendingIntent intent = 
                    PendingIntent.getActivity(ShowNotification.this, 
                            0, msgIntent, 
                            Intent.FLAG_ACTIVITY_NEW_TASK); 
                
                msg.defaults |= Notification.DEFAULT_SOUND;
                msg.flags |= Notification.FLAG_AUTO_CANCEL;
                
                msg.setLatestEventInfo(context, 
                        contentTitle, contentText, intent); 
                mNManager.notify(NOTIFY_ID, msg); 
            } 
        }); 

        stopSimple.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                mNManager.cancel(NOTIFY_ID); 
            } 
        }); 
        
        Button startBigPic = (Button)findViewById(R.id.startBigPic); 
        Button stopBigPic = (Button)findViewById(R.id.stopBigPic); 
        
        startBigPic.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
        

            	Context context = getApplicationContext(); 
                CharSequence contentTitle = "Show Big Notification Example"; 
                CharSequence contentText = "Browse Android Cookbook Site"; 
                
                Intent msgIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.pearson.com")); 
                PendingIntent intent = 
                    PendingIntent.getActivity(ShowNotification.this, 
                            0, msgIntent, 
                            Intent.FLAG_ACTIVITY_NEW_TASK); 
        
            	NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            	builder.setSmallIcon(R.drawable.icon);
            	builder.setContentTitle(contentTitle);
            	builder.setContentText(contentText);
            	                
            	NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle();
            	Bitmap bigPicture= BitmapFactory.decodeResource(getResources(), R.drawable.bigpicture);
            	pictureStyle.bigPicture(bigPicture);
            	            
            	builder.setStyle(pictureStyle);
            	builder.setContentIntent(intent);
 
                mNManager.notify(NOTIFY_ID+1,builder.build()); 
            } 
        }); 
        stopBigPic.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                mNManager.cancel(NOTIFY_ID+1); 
            } 
        }); 
        
        Button startInbox = (Button)findViewById(R.id.startInbox); 
        Button stopInbox = (Button)findViewById(R.id.stopInbox); 
        
        startInbox.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
        

            	Context context = getApplicationContext(); 
                CharSequence contentTitle = "Show Big Notification Example"; 
                CharSequence contentText = "Browse Android Cookbook Site"; 
                
                Intent msgIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.pearson.com")); 
                PendingIntent intent = 
                    PendingIntent.getActivity(ShowNotification.this, 
                            0, msgIntent, 
                            Intent.FLAG_ACTIVITY_NEW_TASK); 
        
            	NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            	builder.setSmallIcon(R.drawable.icon);
            	builder.setContentTitle(contentTitle);
            	builder.setContentText(contentText);
            	                
            	NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            	
            	for(int i=0;i<4;i++){
            		inboxStyle.addLine("subevent #"+i);
            	}
                
            	builder.setStyle(inboxStyle);
            	builder.setContentIntent(intent);
 
                mNManager.notify(NOTIFY_ID+2,builder.build()); 
            } 
        }); 
        stopInbox.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                mNManager.cancel(NOTIFY_ID+2); 
            } 
        }); 

        
        Button startProgress = (Button)findViewById(R.id.startProgress); 
        Button stopProgress = (Button)findViewById(R.id.stopProgress); 
        
        startProgress.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
        

            	Context context = getApplicationContext(); 
                CharSequence contentTitle = "Show Big Notification Example"; 
                CharSequence contentText = "Browse Android Cookbook Site"; 
                
                Intent msgIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.pearson.com")); 
                PendingIntent intent = 
                    PendingIntent.getActivity(ShowNotification.this, 
                            0, msgIntent, 
                            Intent.FLAG_ACTIVITY_NEW_TASK); 
        
            	NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            	builder.setSmallIcon(R.drawable.icon);
            	builder.setContentTitle(contentTitle);
            	builder.setContentText(contentText);
            	                
            	NotificationCompat.Action
            	InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            	
            	for(int i=0;i<4;i++){
            		inboxStyle.addLine("subevent #"+i);
            	}
                
            	builder.setStyle(inboxStyle);
            	builder.setContentIntent(intent);
 
                mNManager.notify(NOTIFY_ID+3,builder.build()); 
            } 
        }); 
        stopProgress.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                mNManager.cancel(NOTIFY_ID+3); 
            } 
        });         
        
        
    } 
}

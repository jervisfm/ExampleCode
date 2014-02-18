package com.cookbook.resultreceiver_service;



import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class ResultReceiverIntentService extends IntentService {

	public static final String EXTRA_RESULT_RECEIVER = "EXTRA_RESULT_RECEIVER";
	
	Notification notification=null;
	private NotificationManager mNManager;
	
	public ResultReceiverIntentService() {
		super("SimpleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ResultReceiver rr=intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
		if(intent.hasExtra("msg")){
			String text= "Echo: "+intent.getStringExtra("msg");
			Bundle resultBundle=new Bundle();
			resultBundle.putString("msg",text);
			rr.send(1, resultBundle);
			
		}
		
	}



}

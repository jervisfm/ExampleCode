package com.androidtablet.threadapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


public class ThreadAppActivity extends Activity {
	static TextView seqNums;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_app);
        seqNums=(TextView)findViewById(R.id.seqnums);
	}

	static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) { 
	    seqNums.setText(msg.obj.toString());        
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++){
                    try {
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.obj=String.valueOf(i);
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

}

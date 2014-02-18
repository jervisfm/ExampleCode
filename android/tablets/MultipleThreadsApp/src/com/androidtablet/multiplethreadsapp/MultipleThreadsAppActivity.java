package com.androidtablet.multiplethreadsapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.os.Message;
import android.os.Handler;

public class MultipleThreadsAppActivity extends Activity {
static	private TextView seqNums1, seqNums2;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_threads_app);
		seqNums1 = (TextView)findViewById(R.id.seqnums1);
		seqNums2 = (TextView)findViewById(R.id.seqnums2);
	}
	
static	Handler handler = new Handler() {
		@Override 
		public void handleMessage(Message msg)		{
			String threadInvoked=msg.getData().getString("threadName");
			if (threadInvoked.equals("thread1"))
		seqNums1.setText(msg.obj.toString());
			if (threadInvoked.equals("thread2"))
				seqNums2.setText(msg.obj.toString());
		}
		};

	    @Override
	    protected void onStart() {
	        super.onStart(); 
	 Thread 	thread1 = new Thread(new Runnable() {
        @Override
		public void run() {
        	 try {
		   for(int i=1;i<=10;i++){
		                        Thread.sleep(1000);
		                        Message msg = new Message();  
		                        Bundle bundle = new Bundle();
		                        bundle.putString("threadName", "thread1");
		                        msg.setData(bundle);
		                        msg.obj=String.valueOf(i);
		                        handler.sendMessage(msg);
	   }
		                    } catch (InterruptedException e) {
		                       e.printStackTrace();
		                    }	
		}
		});

 Thread thread2 = new Thread(new Runnable()		{
    @Override
		public void run() {
	     try {
		   for(int i=1;i<=10;i++){
		                    	 Message msg = new Message();
		                        Thread.sleep(2000);
		                        Bundle bundle = new Bundle();
		                        bundle.putString("threadName", "thread2");
		                        msg.setData(bundle);
		                        msg.obj=String.valueOf(i);
		                        handler.sendMessage(msg);
		   }
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		}
		});
 thread1.start();
 thread2.start();
	}
}

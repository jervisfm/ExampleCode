package com.cookbook.simple_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class SimpleService extends Service {
    private static final String LOG_TAG = SimpleService.class.getSimpleName();

	@Override
    public IBinder onBind(Intent arg0) {
          return null;
    }

    boolean paused = false;
	private WakeLock mWakeLock=null;
    
    @Override
    public void onCreate() {
          super.onCreate();
          Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
          setWakeLock();
          paused = false;
          Thread initBkgdThread = new Thread(new Runnable() {
              public void run() {                      
                  play_music();
              }     
          });
          initBkgdThread.start();
    }

    private void setWakeLock() {
    	PowerManager powerManager=(PowerManager)getSystemService(Context.POWER_SERVICE);
    	mWakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOG_TAG);
    	mWakeLock.acquire();		
	}

	@Override
    public void onDestroy() {
          super.onDestroy();
          releaseWakeLock();
          Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
          paused = true;
    }
    
    private void releaseWakeLock() {
	 	if(mWakeLock!=null && mWakeLock.isHeld())
    	{
	 		mWakeLock.release();
    	}
	}

	int[] notes = {R.raw.c5, R.raw.b4, R.raw.a4, R.raw.g4};
    int NOTE_DURATION = 400; //millisec
    MediaPlayer m_mediaPlayer;
    private void play_music() {
        for(int ii=0; ii<12; ii++) {
            //check to ensure main activity not paused
            if(!paused) {
                if(m_mediaPlayer != null) {m_mediaPlayer.release();} 
                m_mediaPlayer = MediaPlayer.create(this, notes[ii%4]);
                m_mediaPlayer.start();
                try {
                    Thread.sleep(NOTE_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

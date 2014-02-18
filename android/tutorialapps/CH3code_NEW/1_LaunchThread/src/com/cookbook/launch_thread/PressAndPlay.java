package com.cookbook.launch_thread;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PressAndPlay extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        

        Button startButton = (Button) findViewById(R.id.trigger);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                //standalone play_music() function call causes
                //main thread to hang.  Instead, create
                //separate thread for time-consuming task
                Thread initBkgdThread = new Thread(new Runnable() {
                    public void run() {                      
                        play_music();
                    }     
                });
                initBkgdThread.start();
            }
        });        
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

    boolean paused = false;
    @Override
    protected void onPause() {
        paused = true;
        super.onPause();
    } 
    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
    }    
}
package com.cookbook.background_timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BackgroundTimer extends Activity {
    //keep track of button presses, a main thread task
    private int buttonPress=0;
    TextView mButtonLabel;

    //counter of time since app started, a background task
    private long mStartTime = 0L;
    private TextView mTimeLabel;
    
    //Handler to handle the message to the timer task
    private Handler mHandler = new Handler();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        

        if (mStartTime == 0L) {
            mStartTime = SystemClock.uptimeMillis(); 
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }

        mTimeLabel = (TextView) findViewById(R.id.text);
        mButtonLabel = (TextView) findViewById(R.id.trigger);

        Button startButton = (Button) findViewById(R.id.trigger);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                mButtonLabel.setText("Pressed " + ++buttonPress + " times");
            }
        });        
    }
    
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            final long start = mStartTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;

            mTimeLabel.setText("" + minutes + ":" + String.format("%02d",seconds));
            mHandler.postDelayed(this, 200);
        }
    };

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }
}
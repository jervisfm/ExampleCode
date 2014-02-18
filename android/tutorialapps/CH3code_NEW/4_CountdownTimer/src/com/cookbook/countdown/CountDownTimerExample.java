package com.cookbook.countdown;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountDownTimerExample extends Activity {
    //keep track of button presses, a main thread task
    private int buttonPress=0;
    TextView mButtonLabel;

    //counter of time since app started, a background task
    private TextView mTimeLabel;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
       
        mTimeLabel = (TextView) findViewById(R.id.text);
        mButtonLabel = (TextView) findViewById(R.id.trigger);
        /*new Timer().schedule(new TimerTask() {
            public void run() {
                mTimeLabel.setText("seconds remaining: " 
                        + sec++);                
            }
        }, 0, 1000);
        */
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTimeLabel.setText("seconds remaining: " 
                        + millisUntilFinished / 1000);
                }     
            public void onFinish() {
                mTimeLabel.setText("done!");
                }  
       }.start();
       
        Button startButton = (Button) findViewById(R.id.trigger);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                mButtonLabel.setText("Pressed " + ++buttonPress + " times");
            }
        });        
    }
}
package com.cookbook.handle_message;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HandleMessage extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);        

        Thread thread = new Thread(this);
        thread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            setContentView(R.layout.main);
        }
    };

    public void run(){
        initializeArrays();
        mHandler.sendEmptyMessage(0);
    }

    final static int NUM_SAMPS = 1000;
    static double[][] correlation;
    void initializeArrays() {
        if(correlation!=null) return;

        correlation = new double[NUM_SAMPS][NUM_SAMPS];
        //calculation
        for(int k=0; k<NUM_SAMPS; k++) {
            for(int m=0; m<NUM_SAMPS; m++) {
                correlation[k][m] = Math.cos(2*Math.PI*(k+m)/1000);
            }
        }
    }
}
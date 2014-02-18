package com.cookbook.runnable_activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EdgeDetection extends Activity implements Runnable {
    int numberOfTimesPressed=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView tv  = (TextView) findViewById(R.id.text);
        //in-place function call causes main thread to hang:
        /* detectEdges(); */
        //instead, create background thread for time-consuming task
        Thread thread = new Thread(EdgeDetection.this);
        thread.start();
        
        Button startButton = (Button) findViewById(R.id.trigger);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){


                tv.setText("Pressed button " + ++numberOfTimesPressed 
                        + " times\nAnd computation loop at " 
                        + "(" + xi + ", " + yi + ") pixels");
            }
        });
    }

    @Override
    public void run() {
        detectEdges();
    }

    //Edge Detection
    int xi, yi;
    private double detectEdges() {
        int x_pixels = 4000;
        int y_pixels = 3000;
        double image_transform=0;
        
        //double loop over pixels for image processing
        //meaningless hyperbolic cosine emulates time-consuming task
        for(xi=0; xi<x_pixels; xi++) {
            for(yi=0; yi<y_pixels; yi++) {
                image_transform = Math.cosh(xi*yi/x_pixels/y_pixels);
            }
        }
        return image_transform;
    }
}
package com.androidtablet.tweeninganimapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.widget.Button;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.AnimationSet;

public class TweeningAnimAppActivity extends Activity {
    ImageView imgView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweening_anim_app);
        Button alphaButton = (Button) findViewById(R.id.alpha_button);
        Button rotateButton = (Button) findViewById(R.id.rotate_button);
        Button scaleButton = (Button) findViewById(R.id.scale_button);
        Button translateButton = (Button) findViewById(R.id.translate_button);
        imgView = (ImageView)findViewById(R.id.imgview);
        rotateButton.setOnClickListener(new View.OnClickListener() {   
             @Override
             public void onClick(View v) {
                 RotateAnimation animation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f); 
                 animation.setDuration(3000);
                 imgView.setAnimation(animation); 
                 animation.start();
             }
         });

        alphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(1.0f, 0.1f);
                animation.setDuration(3000);
                imgView.setAnimation(animation); 
                animation.start();
            }
        }); 

        scaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet set = new AnimationSet(true);
                Animation animation1 = new ScaleAnimation(1.0f, 2.0f,1.0f, 2.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation1.setDuration(3000);
                set.addAnimation(animation1);
                Animation animation2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation2.setDuration(3000);
                animation2.setStartOffset(3000);
                set.addAnimation(animation2);
                imgView.startAnimation(set); 
            }
        });
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet set = new AnimationSet(true);
                Animation animation1 = new TranslateAnimation(0,-150,0,0); 
                animation1.setDuration(3000);
                animation1.setFillAfter(true); 
                set.addAnimation(animation1);
                Animation animation2 = new TranslateAnimation(0,0,0,200);
                animation2.setDuration(3000);
                animation2.setStartOffset(3000);
                animation2.setFillAfter(true); 
                set.addAnimation(animation2);
                imgView.startAnimation(set); 
            }
        });
    }
}

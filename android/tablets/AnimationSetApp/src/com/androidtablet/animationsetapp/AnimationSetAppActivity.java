package com.androidtablet.animationsetapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationSetAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set_app);
        ImageView imgView = (ImageView)findViewById(R.id.imgview);
        AnimationSet animSet = new AnimationSet(true);
      /*  Animation animation1 = new ScaleAnimation(1.0f, 2.0f,1.0f, 2.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation1.setDuration(3000);
        animSet.addAnimation(animation1);
        Animation animation2 = new ScaleAnimation(1.0f, 0.5f,1.0f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation2.setDuration(3000);
        animation2.setStartOffset(3000);
        animSet.addAnimation(animation2);
        imgView.startAnimation(animSet);   */
        animSet.setInterpolator(new AccelerateInterpolator());
        TranslateAnimation slide1 = new TranslateAnimation(0, 100, 0, 200); 
        slide1.setStartOffset(0);
        slide1.setDuration(1000);
        animSet.addAnimation(slide1);
        TranslateAnimation slide2 = new TranslateAnimation(0, 100, 0, -200); 
        slide2.setStartOffset(1000);
        slide2.setDuration(1000);
        animSet.addAnimation(slide2);
        TranslateAnimation slide3 = new TranslateAnimation(0, 100, 0, 200); 
        slide3.setStartOffset(2000);
        slide3.setDuration(1000);
        animSet.addAnimation(slide3);
        animSet.setFillAfter(true);
        imgView.startAnimation(animSet);

    }  
}

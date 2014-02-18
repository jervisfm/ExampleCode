package com.androidtablet.multianimapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.widget.TextView;

public class MultiAnimAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_anim_app);  
        ImageView imgView = (ImageView)findViewById(R.id.imgview);
        final TextView textView = (TextView)this.findViewById(R.id.textview);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(imgView, "scaleX",1f, 2f),  ObjectAnimator.ofFloat(imgView,  "rotation", 0f, 360f));
        animatorSet.setDuration(20000);
        animatorSet.start();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 1f);
        animatorSet.playSequentially(fadeOut,fadeIn);
        animatorSet.setDuration(5000); 
        animatorSet.start();
    }  
}

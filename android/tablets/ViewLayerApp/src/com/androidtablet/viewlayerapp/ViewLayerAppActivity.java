package com.androidtablet.viewlayerapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.View;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;

public class ViewLayerAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layer_app);
        final TextView textView = (TextView)this.findViewById(R.id.textview);
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) { 
                textView.setLayerType(View.LAYER_TYPE_HARDWARE,  null);
                ObjectAnimator animator =ObjectAnimator.ofFloat(textView, View.ALPHA, 0, 1);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textView.setLayerType(View.LAYER_TYPE_NONE, null);
                    }
                });
                animator.setDuration(2000);
                animator.start();
            }
        });
    }  
}

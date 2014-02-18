package com.androidtablet.valueanimatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.animation.ValueAnimator;
import android.widget.TextView;
import android.animation.ValueAnimator.AnimatorUpdateListener;

public class ValueAnimatorAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator_app);
        final TextView textView = (TextView)this.findViewById(R.id.textview);
        Button valueAnimatorButton = (Button) findViewById(R.id.value_anim_button);
        valueAnimatorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {    
                ValueAnimator anim = ValueAnimator.ofFloat(1f, 3.5f);
                anim.setRepeatCount(ValueAnimator.INFINITE);
                anim.setRepeatMode(ValueAnimator.REVERSE);
                anim.setDuration(3000); 
                anim.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (Float)animation.getAnimatedValue();
                        textView.setTextScaleX(value);
                    }
                });
                anim.start();     
            }
        });
    }
}

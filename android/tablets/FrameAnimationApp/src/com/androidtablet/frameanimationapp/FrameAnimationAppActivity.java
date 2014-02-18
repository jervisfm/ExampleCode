package com.androidtablet.frameanimationapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.AnimationDrawable;

public class FrameAnimationAppActivity extends Activity {
    AnimationDrawable animation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation_app);
        final ToggleButton startStopButton = (ToggleButton) findViewById(R.id.startstop_button);
        final ImageView imgView = (ImageView)findViewById(R.id.imgview);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStopButton.isChecked()) {
                    imgView.setBackgroundResource(R.anim.frame_anim);
                    animation =  (AnimationDrawable) imgView.getBackground();
                    animation.start();
                }
                else 
                    animation.stop();
            }
        });
    }
}

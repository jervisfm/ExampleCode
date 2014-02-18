package com.androidtablet.objectanimatorapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.View;
import android.animation.ObjectAnimator;

public class ObjectAnimatorAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator_app);
        final TextView textView = (TextView)this.findViewById(R.id.textview);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle_button); 
        toggleButton.setText("Rotate Clockwise");
        final ObjectAnimator objAnim = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f).setDuration(5000);
        toggleButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    toggleButton.setText("Rotate Clockwise followed by Counterclockwise");
                    objAnim.setRepeatMode(ObjectAnimator.RESTART);     
                }
                else  {
                    toggleButton.setText("Rotate Clockwise");
                    objAnim.setRepeatMode(ObjectAnimator.REVERSE);          
                }
                objAnim.setRepeatCount(ObjectAnimator.INFINITE);
                objAnim.start();    
            }
        });
    }
}

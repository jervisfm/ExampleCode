package com.cookbook.propertyanimation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnShift;
	Button btnRotate;
	Button btnSling;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnShift = (Button)this.findViewById(R.id.button);
		btnRotate = (Button)this.findViewById(R.id.button1);
		btnSling = (Button)this.findViewById(R.id.button2);
		
		btnShift.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int start = Color.rgb(0xcc, 0xcc, 0xcc);
			    int end = Color.rgb(0x00, 0xff, 0x00);
			    ValueAnimator va = ObjectAnimator.ofInt(btnShift, "backgroundColor", start, end);
			    va.setDuration(750);
			    va.setRepeatCount(1);
			    va.setRepeatMode(ValueAnimator.REVERSE);
			    va.setEvaluator(new ArgbEvaluator());
			    va.start();
			}
		});
		
		btnRotate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// use ValueAnimator
				/*
				ValueAnimator va = ObjectAnimator.ofFloat(btnRotate, "rotation", 0f, 360f);
				va.setDuration(750);
			    va.start();
			    */
			    // or use an xml defined animation
				btnRotate.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.animator.rotation));
			}
		});
		
		btnSling.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				btnSling.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.animator.sling));
			}
		});
		
	}

}

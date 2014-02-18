package com.androidtablet.timehomewidgetapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TimeHomeWidgetAppActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_home_widget_app);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_time_home_widget_app, menu);
		return true;
	}

}

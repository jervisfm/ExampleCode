package com.androidtablet.actionbaronolderapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.os.Build;
import android.app.ActionBar;

public class ActionBarOnOlderAppActivity extends Activity {
	 ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_on_older_app);
        TextView textView=(TextView)findViewById(R.id.textview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        else
        	textView.setText("Press MENU button to display menu");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater()
				.inflate(R.menu.activity_action_bar_on_older_app, menu);
		return true;
	}

}

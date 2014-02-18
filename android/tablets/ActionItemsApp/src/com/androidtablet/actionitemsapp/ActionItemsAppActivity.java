package com.androidtablet.actionitemsapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

public class ActionItemsAppActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_items_app);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_action_items_app, menu);
		return true;
	}
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {        
	        switch (item.getItemId()) {
	        case  R.id.create : Intent intent = new Intent(this, CreateActivity.class);               
	        startActivity(intent);                              
	        break;
	            default: return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }
}

package com.androidtablet.actionitemsapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class CreateActivity extends Activity {
    @Override  
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create); 
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {        
        switch (item.getItemId()) {
            case  (android.R.id.home) : Intent intent = new Intent(this, ActionItemsAppActivity.class);               
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
                startActivity(intent);                              
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }*/
}

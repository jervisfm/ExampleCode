package com.androidtablet.actionviewapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.util.Log;

public class ActionViewAppActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_view_app);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_action_view_app, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new OnQueryTextListener(){
   	    @Override
            public boolean onQueryTextChange(String newText) { 
                Log.d("New Text:", newText);
                return false; 
            } 
            @Override
            public boolean onQueryTextSubmit(String query) { 
    	        Log.d("Final Text:", query);
    	        return false; 
    	    } 
        });
        return true;
    } 
}

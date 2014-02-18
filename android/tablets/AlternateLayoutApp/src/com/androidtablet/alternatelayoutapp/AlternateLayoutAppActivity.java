package com.androidtablet.alternatelayoutapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class AlternateLayoutAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_layout_app);
        if(getResources().getDisplayMetrics().widthPixels > 
            getResources().getDisplayMetrics().heightPixels) {  
            Toast.makeText(this,"Screen switched to Landcape mode",Toast.LENGTH_SHORT).show(); 
        } 
        else 
            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show(); 
    }
}

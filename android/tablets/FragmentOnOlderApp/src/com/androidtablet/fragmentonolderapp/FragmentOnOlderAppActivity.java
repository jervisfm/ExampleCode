package com.androidtablet.fragmentonolderapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

public class FragmentOnOlderAppActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_on_older_app);
        FragmentManager fragmentManager =   getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(null==fragmentManager.findFragmentById((R.id.fragment_container))){
            MyFragmentActivity fragment = new MyFragmentActivity();
            fragmentTransaction.add(R.id.fragment_container, fragment);  
        }
        fragmentTransaction.commit();        
    }
}

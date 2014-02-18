package com.androidtablet.activitylifecycleapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class ActivityLifecycleAppActivity extends Activity {
    private static final String tag = "State: ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_lifecycle_app);
        Log.d(tag,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag,"onResume");
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag,"onPause");
    }  
 
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag,"onStop");
    };
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag,"onDestroy");
    };
}

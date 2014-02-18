package com.cookbook.simple_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SimpleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  

        Button startButton = (Button) findViewById(R.id.Button01);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startService(new Intent(SimpleActivity.this, SimpleService.class));
            }
        });        

        Button stopButton = (Button)findViewById(R.id.Button02);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                stopService(new Intent(SimpleActivity.this,SimpleService.class));
            }                 
        });
    }
}
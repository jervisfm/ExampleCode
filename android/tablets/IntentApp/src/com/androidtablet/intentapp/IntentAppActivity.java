package com.androidtablet.intentapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class IntentAppActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_app);
        Button startButton = (Button)this.findViewById(R.id.start_button);
        startButton.setOnClickListener(new Button.OnClickListener(){ 
             public void onClick(View v)  {
                 startActivity(new Intent(getBaseContext(), WelcomeActivity.class));
             } 
        });  
    } 
}

package com.androidtablet.communicatedataapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class WelcomeActivity extends Activity {
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);    
        final TextView welcomeMsg=(TextView)findViewById(R.id.welcomemsg);
        Bundle extras = getIntent().getExtras();  
        if(extras !=null) { 
            String userName=extras.getString("username");
            welcomeMsg.setText("Welcome "+userName+" !");
        }
        Button gobackButton = (Button)this.findViewById(R.id.goback_button);
        gobackButton.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });  
    }    
}

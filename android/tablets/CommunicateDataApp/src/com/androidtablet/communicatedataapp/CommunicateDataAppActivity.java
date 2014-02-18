package com.androidtablet.communicatedataapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class CommunicateDataAppActivity extends Activity {
    private static final int WELCOME_REQUEST_CODE = 0;
    TextView response;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate_data_app);
        Button startButton = (Button)this.findViewById(R.id.start_button);
        final EditText userName=(EditText)findViewById(R.id.user_name);
        response=(TextView)findViewById(R.id.response);
        startButton.setOnClickListener(new Button.OnClickListener(){ 
             public void onClick(View v)  {
             if(userName.getText().length() >0) {
                 Bundle dataBundle = new Bundle(); 
                 dataBundle.putString("username", userName.getText().toString());
                 Intent welcomeIntent = new Intent(getBaseContext(), WelcomeActivity.class); 
                 welcomeIntent.putExtras(dataBundle);
                 startActivityForResult(welcomeIntent, WELCOME_REQUEST_CODE); 
             }
             else
                 Toast.makeText(CommunicateDataAppActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            } 
        });    
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); 
        if (requestCode ==WELCOME_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                response.setText("Back from the WelcomeActivity");
            }
        }
    }
}

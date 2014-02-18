package com.cookbook.simple_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SimpleActivity extends Activity {
	EditText editText;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  

       editText=(EditText) findViewById(R.id.editText1);
        
        Button sendButton = (Button) findViewById(R.id.Button01);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	Intent intent=new Intent(SimpleActivity.this, SimpleIntentService.class);
            	intent.putExtra("msg",editText.getText().toString());
                startService(intent);
            }
        });        


    }
}
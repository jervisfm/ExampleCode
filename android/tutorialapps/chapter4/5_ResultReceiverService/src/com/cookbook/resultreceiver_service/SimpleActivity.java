package com.cookbook.resultreceiver_service;

import com.cookbook.resultreceiver_service.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleActivity extends Activity {
	EditText editText;
    
	Handler handler=new Handler();
	
	ResultReceiver resultReceiver=new ResultReceiver(handler){

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			Toast.makeText(SimpleActivity.this, resultData.getString("msg"), Toast.LENGTH_SHORT).show();
		}
		
	};
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  

       editText=(EditText) findViewById(R.id.editText1);
        
        Button sendButton = (Button) findViewById(R.id.Button01);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	Intent intent=new Intent(SimpleActivity.this, ResultReceiverIntentService.class);
            	intent.putExtra("msg",editText.getText().toString());
            	intent.putExtra(ResultReceiverIntentService.EXTRA_RESULT_RECEIVER,resultReceiver);
                startService(intent);
            }
        });        


    }
}
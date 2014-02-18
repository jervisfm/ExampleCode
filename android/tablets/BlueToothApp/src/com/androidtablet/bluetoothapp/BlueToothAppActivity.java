package com.androidtablet.bluetoothapp;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;
import android.content.Intent;

public class BlueToothAppActivity extends Activity {
	 private static final int REQUEST_ENABLE_BT = 0;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_tooth_app);
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        if (bluetoothAdapter == null) {
	            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	        }
	        
	        if (bluetoothAdapter.isEnabled()) 
	        	 Toast.makeText(this, "Bluetooth is Ready", Toast.LENGTH_LONG).show();   
	        else{
	        	  Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	        }	       
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (resultCode == RESULT_OK) {        
                if(requestCode == REQUEST_ENABLE_BT){
                 	 Toast.makeText(this, "Bluetooth is Enabled", Toast.LENGTH_LONG).show();   
                }
                else{
                	 Toast.makeText(this, "Bluetooth could not be Enabled", Toast.LENGTH_LONG).show();   
               }
            }
        }
}

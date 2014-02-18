package com.androidtablet.bluetoothpairedlistapp;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.bluetooth.BluetoothDevice;
import java.util.Set;

public class BlueToothPairedListAppActivity extends Activity {
	 private BluetoothAdapter bluetoothAdapter = null;
	 private static final int REQUEST_ENABLE_BT = 0;
	 private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_tooth_paired_list_app);
		  textView = (TextView) findViewById(R.id.paired_list);
textView.setText("");
		  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        if (bluetoothAdapter == null) {
	            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	        }
	        if (!bluetoothAdapter.isEnabled()) {
	            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	        }   
	        else
	        {
	   	 Toast.makeText(this, "Bluetooth is Ready", Toast.LENGTH_LONG).show();
	   	 dispInfo();
	        }
	 
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {        
            if(requestCode == REQUEST_ENABLE_BT){
             	 Toast.makeText(this, "Bluetooth is Enabled", Toast.LENGTH_LONG).show();  
             	 dispInfo();
            }
        }
        else
        {
            Toast.makeText(this, "Error: Bluetooth is not Enabled", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        }
	
	protected void dispInfo()
	{
      	 textView.append("Adapter: " + bluetoothAdapter.toString() + 
	   	 "\nName: " + bluetoothAdapter.getName() + 
	   	 "\nAddress: " + bluetoothAdapter.getAddress());	
	         bluetoothAdapter.startDiscovery();
	         textView.append("\n\nDevices Paired:");
	        Set<BluetoothDevice> devices =  bluetoothAdapter.getBondedDevices();
	        for (BluetoothDevice device : devices) {
	             textView.append("\n\nFound device: " + device.getName() + "\nAddress: "
	                    + device.getAddress());
	        }
    }
}



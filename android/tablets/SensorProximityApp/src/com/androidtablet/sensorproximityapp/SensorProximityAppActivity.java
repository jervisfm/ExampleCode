package com.androidtablet.sensorproximityapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorProximityAppActivity extends Activity implements SensorEventListener {
    TextView distanceView;
    SensorManager sensorManager;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_proximity_app);
	    distanceView=(TextView)findViewById(R.id.distance);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
	}

    @Override  
    protected void onResume() {  
        super.onResume();  
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);       
    }  

    @Override  
    protected void onStop()  {  
        sensorManager.unregisterListener(this);  
        super.onStop();  
    }  

	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }

	    public void onSensorChanged(SensorEvent event) {
	        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY) {
	            float d=event.values[0];
	            distanceView.setText("Distance is: "+d);
	        }    
	    }

}

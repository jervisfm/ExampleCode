package com.androidtablet.sensorslistapp;

import android.os.Bundle;
import android.app.Activity;
import android.hardware.SensorManager;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.hardware.Sensor;
import android.widget.ListView;

public class SensorsListAppActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list_app);
        ListView sensorsList = (ListView)findViewById(R.id.sensors_list);
		SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listOfSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<String> arrayListSensors = new ArrayList<String>();      
        for(int i=0; i<listOfSensors.size(); i++)
        	arrayListSensors.add(((Sensor) listOfSensors.get(i)).getName());        
        ArrayAdapter<String> arrayAdpt= new ArrayAdapter<String>(this, R.layout.list_item, arrayListSensors);
        sensorsList.setAdapter(arrayAdpt);   
	}
}

package com.androidtablet.sensorgyroscopeapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class SensorGyroscopeAppActivity extends Activity implements SensorEventListener  {
    TextView xAxisView,yAxisView, zAxisView;
    SensorManager sensorManager;
    float angleX, angleY, angleZ;
    private long previousTime =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_gyroscope_app);
        xAxisView=(TextView)findViewById(R.id.xaxisview);
        yAxisView=(TextView)findViewById(R.id.yaxisview);
        zAxisView=(TextView)findViewById(R.id.zaxisview); 
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
    }
    
   @Override  
    protected void onResume() {  
        super.onResume();  
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);       
    }  

    @Override  
    protected void onStop()  {  
        sensorManager.unregisterListener(this);  
        super.onStop();  
    }  

    @Override  
    public void onAccuracyChanged(Sensor arg0, int arg1)   {   }  
    public void onSensorChanged(SensorEvent event) {
        if (previousTime != 0) {
            final float timeDiff = (event.timestamp - previousTime) *  1.0f / 1000000000.0f;
            angleX += event.values[0] * timeDiff;
            angleY += event.values[1] * timeDiff;
            angleZ += event.values[2] * timeDiff;
            xAxisView.setText("Orientation X : "+angleX);
            yAxisView.setText("Orientation Y : "+angleY);
            zAxisView.setText("Orientation Z : "+angleZ);
        }
        previousTime = event.timestamp;
    }
}

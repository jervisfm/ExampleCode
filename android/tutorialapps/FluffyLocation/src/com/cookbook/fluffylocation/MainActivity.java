package com.cookbook.fluffylocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textview_main);
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String locationprovider = locationManager.getBestProvider(criteria, true);
		Location mLocation = locationManager.getLastKnownLocation(locationprovider);
		try {
			tv.setText("Last Location\nlat:" + mLocation.getLatitude() + "\nlong: " + mLocation.getLongitude() + "\naccuracy: " + mLocation.getAccuracy() + "m" );
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(), "Please allow apps to use your location", Toast.LENGTH_LONG).show();
			enableLocationSettings();
		}
	}

	@Override
	protected void onStart() {
	    super.onStart();

	    // This verification should be done during onStart() because the system calls
	    // this method when the user returns to the activity, which ensures the desired
	    // location provider is enabled each time the activity resumes from the stopped state.
	    LocationManager locationManager =
	            (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    if (!gpsEnabled) {
	        // Build an alert dialog here that requests that the user enable
	        // the location services, then when the user clicks the "OK" button,
	        // call enableLocationSettings()
	    	Toast.makeText(getApplicationContext(), "GPS is disabled, will use coarse location", Toast.LENGTH_LONG).show();
	    }
	}

	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}

}


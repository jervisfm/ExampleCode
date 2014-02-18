package com.cookbook.fluffylocation;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

import android.app.Application;
import android.util.Log;

public class FluffyApplication extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        // show debugging information
        Log.d("FluffyApplication", "onCreate()");

        LocationLibrary.showDebugOutput(true);

        // default call would be the following:
        // LocationLibrary.initialiseLibrary(getBaseContext(), "com.cookbook.fluffylocation");

        // For testing make request every 1 minute, and force a location update
        // if one hasn't happened in the last 2 minutes.
        LocationLibrary.initialiseLibrary(getBaseContext(), 60 * 1000, 2 * 60 * 1000, "com.cookbook.fluffylocation");
    }
}

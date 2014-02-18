package cc.dividebyzero.android.cookbook.chapter14.receive;

import android.content.Context;


public class GCMBroadcastReceiver extends com.google.android.gcm.GCMBroadcastReceiver {

	@Override
	protected String getGCMIntentServiceClassName(Context context) {
		android.util.Log.v("GCM_ORIG","class name>>"+super.getGCMIntentServiceClassName(context)+"<<");
		
		return GCMService.class.getCanonicalName();
	}

}

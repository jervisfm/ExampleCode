package cc.dividebyzero.android.cookbook.chapter14.receive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import cc.dividebyzero.android.cookbook.chapter14.AppConstants;

import com.google.android.gcm.GCMBaseIntentService;



public class GCMService extends GCMBaseIntentService {

	private static final String LOG_TAG =GCMService.class.getSimpleName();

	private Handler mToaster=new Handler( new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			Toast.makeText(GCMService.this, ((String)msg.obj), Toast.LENGTH_SHORT).show();	
			return true;
		}
	});
	
	@Override
	protected void onError(final Context ctx,final String errorMsg) {
		android.util.Log.v(LOG_TAG,"error registering device; "+errorMsg);
		
	}

	@Override
	protected void onMessage(final Context ctx,final Intent intent) {
		android.util.Log.v(LOG_TAG,"on Message, Intent="+intent.getExtras().toString());
		Message msg=mToaster.obtainMessage(1, -1, -1, intent.getStringExtra("msg"));
		mToaster.sendMessage(msg);
	}

	@Override
	protected void onRegistered(Context ctx, String gcmRegistrationId) {
		android.util.Log.v(LOG_TAG,"onRegistered: gcmRegistrationId>>"+gcmRegistrationId+"<<");
		sendRegistrationToServer(gcmRegistrationId);		
	}


	@Override
	protected void onUnregistered(Context ctx, String gcmRegistrationId) {
		
		sendDeregistrationToServer(gcmRegistrationId);
	}
	
	
	
	private void sendRegistrationToServer(String gcmRegistrationId) {
		SharedPreferences.Editor editor=getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE).edit();
		editor.putString(AppConstants.PREF_REGISTRATION_ID, gcmRegistrationId);
		editor.commit();
		
	}

	private void sendDeregistrationToServer(String gcmRegistrationId) {
		SharedPreferences.Editor editor=getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

}

package cc.dividebyzero.android.cookbook.chapter14;

import cc.dividebyzero.android.cookbook.chapter14.send.SendGCMTask;

import com.google.android.gcm.GCMRegistrar;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GCMPushReceiver extends Activity{

	
	
	private static final String LOG_TAG = GCMPushReceiver.class.getSimpleName();
	private EditText mMessage;


	public void onCreate(Bundle savedState){
		super.onCreate(savedState);
		setContentView(R.layout.gcm_acv);
		mMessage=(EditText)findViewById(R.id.message);
		registerGCM();
	}

	
	private void registerGCM() {

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
		  GCMRegistrar.register(this, getString(R.string.sender_id));
		} else {
		  android.util.Log.v(LOG_TAG, "Already registered");
		}	
	}
	
	public void sendGCMMessage(final View view){
		final String message=mMessage.getText().toString();
		SendGCMTask sendTask=new SendGCMTask(getApplicationContext());
		final String targetId=getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE).getString(AppConstants.PREF_REGISTRATION_ID,null);
		sendTask.execute(message,targetId);
	}
	
}

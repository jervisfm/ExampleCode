package cc.dividebyzero.android.cookbook.chapter14.send;

import java.io.IOException;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import cc.dividebyzero.android.cookbook.chapter14.AppConstants;
import cc.dividebyzero.android.cookbook.chapter14.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class SendGCMTask extends AsyncTask<String, Void, Boolean> {

	private static final int MAX_RETRY = 5;
	private static final String LOG_TAG = SendGCMTask.class.getSimpleName();
	private Context mContext;
	private String mApiKey;

	public SendGCMTask(final Context contex){
		mContext=contex;
		mApiKey=mContext.getString(R.string.api_key);
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		final String message=params[0];
		final String targetId=params[1];
		android.util.Log.v(LOG_TAG,"message>>"+message+"<< targetId>>"+targetId+"<<");
		
		Sender sender = new Sender(mApiKey);
		Message gcmMessage = new Message.Builder().addData("msg",message).build();
		
		Result result;

		try {
			result = sender.sendNoRetry(gcmMessage, targetId);
			
			if (result.getMessageId() != null) {
				 String canonicalRegId = result.getCanonicalRegistrationId();

				 SharedPreferences.Editor editor=mContext.getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE).edit();
				 String error = result.getErrorCodeName();
				 
				 if (canonicalRegId != null) 
				 {
				   // same device has more than on registration ID: update database
					 editor.putString(AppConstants.PREF_REGISTRATION_ID, canonicalRegId);
					 editor.commit();
					 
				 }else if (error!=null && error.equals(Constants.ERROR_NOT_REGISTERED))
				 {
				   // application has been removed from device - unregister database
					 editor.clear();
					 editor.commit();
				 }
				 
			}
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}

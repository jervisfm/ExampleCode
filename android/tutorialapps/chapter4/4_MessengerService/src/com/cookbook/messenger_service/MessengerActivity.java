package com.cookbook.messenger_service;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessengerActivity extends Activity {
	private static final String LOG_TAG = null;

	EditText editText;
    
	/** Messenger for communicating with service. */
	Messenger mServiceMessenger = null;
	/** Flag indicating whether we have called bind on the service. */
	boolean mIsBound;
	
	

	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==MessageControlledService.MSG_RESULT){
				Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
		}

	};

	Messenger mLocalMessageReceiver=new Messenger(mHandler);
	
	/**
	 * Class for interacting with the main interface of the service.
	 */
	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
        public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. We are communicating with our
			// service through an IDL interface, so get a client-side
			// representation of that from the raw service object.
			mServiceMessenger = new Messenger(service);

			// We want to monitor the service for as long as we are
			// connected to it.
			try {
				Message msg = Message.obtain(null,MessageControlledService.MSG_REGISTER_CLIENT);
				msg.replyTo = mLocalMessageReceiver;
				mServiceMessenger.send(msg);

			} catch (RemoteException e) {
				// In this case the service has crashed before we could even
				// do anything with it; we can count on soon being
				// disconnected (and then reconnected if it can be restarted)
				// so there is no need to do anything here.
			}

			Log.v(LOG_TAG, "sercive connected");
		}

		@Override
        public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			mServiceMessenger = null;
			Log.v(LOG_TAG, "sercive disconnected");

		}
	};



	@Override
	protected void onResume() {
        super.onResume();
        bindMessengerService();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unbindAccountService();
	}

	void bindMessengerService() {
		Log.v(LOG_TAG,"binding accountservice");
		// Establish a connection with the service. We use an explicit
		// class name because there is no reason to be able to let other
		// applications replace our component.
		bindService(new Intent(getApplicationContext(), MessageControlledService.class),
				mServiceConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;

	}

	protected void unbindAccountService() {
		if (mIsBound) {
			Log.v(LOG_TAG,"unbinding accountservice");
			// If we have received the service, and hence registered with
			// it, then now is the time to unregister.
			if (mServiceMessenger != null) {
				try {
					Message msg = Message.obtain(null,MessageControlledService.MSG_UNREGISTER_CLIENT);
					
					msg.replyTo = mServiceMessenger;
					mServiceMessenger.send(msg);
				} catch (RemoteException e) {
					// There is nothing special we need to do if the service
					// has crashed.
				}
			}

			// Detach our existing connection.
			unbindService(mServiceConnection);
			mIsBound = false;

		}
	}

	protected boolean sendMessageToService(final int what, final int arg1,final int arg2, final Object object) {
		try {
			Message msg = Message.obtain(null, what, arg1, arg2, object);
			mServiceMessenger.send(msg);
		} catch (RemoteException e) {
			Log.e(LOG_TAG,"unable to send message to account service",e);
			//retry binding. 
			bindMessengerService();
			return false;
		}
		return true;

	}	
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		startService(new Intent(getApplicationContext(), MessageControlledService.class));
        setContentView(R.layout.main);  

       editText=(EditText) findViewById(R.id.editText1);
        
        Button sendButton = (Button) findViewById(R.id.Button01);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	String text=editText.getText().toString();
            	sendMessageToService(MessageControlledService.MSG_FIRST_USER,-1,-1,text);
            }
        });        


    }
	
	
}
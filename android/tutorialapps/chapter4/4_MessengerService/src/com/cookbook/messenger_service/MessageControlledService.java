package com.cookbook.messenger_service;


import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;

/**
 * MessageControlledService is an abstract service implementation that communicates with clients
 * via Messgenger objects. Messages are passed directly into the Handler of the Server/Client.
 */
public class MessageControlledService extends Service {

	public static final int MSG_INVALID             = Integer.MIN_VALUE;
	public static final int MSG_REGISTER_CLIENT     = MSG_INVALID+1;
	public static final int MSG_UNREGISTER_CLIENT   = MSG_INVALID+2;
	public static final int MSG_RESULT 				= MSG_INVALID+3;
	
	/** make sure your message constants are MSG_FIRST_USER+n**/
	public static final int MSG_FIRST_USER=1;
	

	
	private static final String LOG_TAG = MessageControlledService.class.getCanonicalName();
	

	/** Keeps track of all current registered clients. */
	ArrayList<Messenger> mClients = new ArrayList<Messenger>();

	/**
	 * Handler of incoming messages from clients.
	 */
	private class CommandHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);

				break;
			default:
				handleNextMessage(msg);
			}
		}
	}

		
	private final Handler mHandler=new CommandHandler();
	
	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	private final Messenger mMessenger = new Messenger(mHandler);
	

	/**
	 * call this to send an arbitrary message to the reqistered clients. 
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param object
	 */
	protected final void sendMessageToClients(final int what,final int arg1,final int arg2, final Object object) {
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
				Message msg = Message.obtain(null, what,arg1,arg2, object);
				mClients.get(i).send(msg);
			} catch (RemoteException e) {
				// The client is dead. Remove it from the list;
				// we are going through the list from back to front
				// so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
		
	
	//------service stuff

	@Override
	public IBinder onBind(Intent arg0) {
		return mMessenger.getBinder();
	}
	

	/**
	 * This is your main method. 
	 * 
	 * @param msg the next message in the queue
	 */
	public  void handleNextMessage(final Message msg){
		String echo="ECHO: "+(String)msg.obj;
		sendMessageToClients(MSG_RESULT, -1, -1, echo);
	}
    
}

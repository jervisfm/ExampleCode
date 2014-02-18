package com.androidtablet.androidbeamapp;

import android.os.Bundle;
import android.app.Activity;
import java.nio.charset.Charset;
import android.nfc.NdefRecord;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.app.PendingIntent;
import android.content.IntentFilter;

public class AndroidBeamAppActivity extends Activity {
	NdefMessage nfcMessage;
	NfcAdapter nfcAdapter;
	PendingIntent pendingIntent;
	IntentFilter[] intentFiltersArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_beam_app);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null)
            Toast.makeText(this, "NFC chip is not available on this device", Toast.LENGTH_LONG).show(); 
        else
            Toast.makeText(this, "NFC chip is available on this device", Toast.LENGTH_LONG).show();  
		nfcAdapter.setNdefPushMessage(nfcMessage, this);
		nfcAdapter.setNdefPushMessageCallback(new CreateNdefMessageCallback() {
			public NdefMessage createNdefMessage(NfcEvent event) {
				String payload = "Text to beam";
				String mimeType = "com.androidtablet.androidbeamapp";
				byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
				  NdefRecord record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,mimeBytes,new byte[0],payload.getBytes()); 
				 nfcMessage = new NdefMessage(new NdefRecord[] { record, NdefRecord.createApplicationRecord("com.androidtablet.androidbeamapp") });  
			return nfcMessage;
			}
			}, this);	
	}
	
	 
	@Override
	public void onResume() {
	super.onResume();
    pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this,getClass()). addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);      	
    IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);  
    intentFiltersArray = new IntentFilter[]{intentFilter}; 
    nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
	Intent intent = getIntent();
	if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
	try {
	Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	NdefMessage ndefMessage = (NdefMessage) messages[0];
	NdefRecord ndefRecord = ndefMessage.getRecords()[0];
	String textReceived = new String(ndefRecord.getPayload());
	Toast.makeText(this, textReceived, Toast.LENGTH_LONG).show(); 
	} catch (Exception e) {
	Log.e("Error:", "Error retrieving beam message.", e);
	}
	}
	}

	 @Override
	  public void onNewIntent(Intent intent) {
	    setIntent(intent);
	  }
	
	 public void onPause() {
		    super.onPause();
		    nfcAdapter.disableForegroundDispatch(this);
		  }
}

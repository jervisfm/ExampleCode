package com.androidtablet.nfcreadtagapp;

import android.os.Bundle;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.nfc.NdefRecord;
import android.widget.Toast;
import android.nfc.Tag;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.tech.Ndef;

public class NFCReadTagAppActivity extends Activity {
	Tag tagDetected;
	NfcAdapter nfcAdapter;
	PendingIntent pendingIntent;
	IntentFilter[] readTagFilters;
	IntentFilter intentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcread_tag_app);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if(nfcAdapter == null)
			Toast.makeText(this, "NFC chip is not available on this device", Toast.LENGTH_LONG).show(); 
		else	
			Toast.makeText(this, "NFC chip is available on this device", Toast.LENGTH_LONG).show();  
		   pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
	         readTagFilters = new IntentFilter[]{intentFilter};
		
		}

	@Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, readTagFilters, null);
    }

	@Override
	protected void onPause() {
	super.onPause();
	if(nfcAdapter != null)
		nfcAdapter.disableForegroundDispatch(this);
	}
	
	 protected void onNewIntent(Intent intent) {
	        if(intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)){                         
	            tagDetected = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	            readFromTag(intent);
	        }
	    }

	 public void readFromTag(Intent intent){
	        Ndef ndef = Ndef.get(tagDetected);
	        try{
	            ndef.connect();
	            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	            if (messages != null) {
	                NdefMessage[] ndefMessages = new NdefMessage[messages.length];
	                for (int i = 0; i < messages.length; i++) {
	                    ndefMessages[i] = (NdefMessage) messages[i];
	                }
	            NdefRecord record = ndefMessages[0].getRecords()[0];
	            byte[] payload = record.getPayload();
	            String text = new String(payload);
	            Toast.makeText(this, text, Toast.LENGTH_LONG).show();  
	            ndef.close();
	        }
	        }
	        catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "Cannot Read From Tag.", Toast.LENGTH_LONG).show();
	        }
	    }
}

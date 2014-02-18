package com.androidtablet.nfcapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import android.nfc.NfcAdapter;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.IntentFilter;
import android.nfc.tech.Ndef;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import java.nio.charset.Charset;
import java.util.Locale;
import android.nfc.Tag;

public class NFCAppActivity extends Activity {
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private IntentFilter[] writeFilters;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if(nfcAdapter == null)
			Toast.makeText(this, "NFC chip is not available on this device", Toast.LENGTH_LONG).show(); 
		else
			Toast.makeText(this, "NFC chip is available on this device", Toast.LENGTH_LONG).show();  
                	pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            		IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            		try {    
            			intentFilter.addDataType("text/plain");
            			} catch (MalformedMimeTypeException e) {}
            		writeFilters = new IntentFilter[] { intentFilter };
	}	
	
	@Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeFilters, null);
    }

	 public void onPause() {
		    super.onPause();
		    nfcAdapter.disableForegroundDispatch(this);
		  }

	 public void onNewIntent(Intent intent) {
			Tag tagDetected = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tagDetected);
			}
	 
	private boolean writeTag(Tag tagDetected) {
			byte[] langBytes = Locale.ENGLISH.getLanguage().getBytes(Charset.forName("US-ASCII"));
			String text = "Text to be written into the Tag";
			byte[] textBytes = text.getBytes(Charset.forName("UTF-8"));	
			byte[] tagId = new byte[0];
		    byte[] payload    = new byte[1 + langBytes.length + textBytes.length];
		    payload[0] = (byte) langBytes.length;
		    System.arraycopy(langBytes, 0, payload, 1, langBytes.length);
		    System.arraycopy(textBytes, 0, payload, 1 + langBytes.length, textBytes.length);
		    NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, tagId, payload);	
		NdefMessage message = new NdefMessage(new NdefRecord[] { record }); 
		try {
		Ndef ndef = Ndef.get(tagDetected);
		if (ndef != null) {
		ndef.connect();
		if (!ndef.isWritable()) {
			Toast.makeText(this, "This is a Read-only Tag.", Toast.LENGTH_LONG).show(); 
		return false;
		}
		int size = message.toByteArray().length;
		if (ndef.getMaxSize() < size) {
			Toast.makeText(this, "Tag doesn't have enough free space.", Toast.LENGTH_LONG).show(); 
		return false;
		}
		ndef.writeNdefMessage(message);
		Toast.makeText(this, "Text written to the Tag successfully.", Toast.LENGTH_LONG).show(); 
		ndef.close(); 
		return true;
		} 
		} catch (Exception e) {
			Toast.makeText(this, "Failed to write Tag", Toast.LENGTH_LONG).show(); 
		}
		return false;
		}		
}

package com.cookbook.nfcwriter;

import java.io.IOException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected NfcAdapter nfcAdapter;
	private Button mainButton;
	private boolean mInWriteMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		mainButton = (Button)findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        
	}

	public void onClick(View v) {
		displayMessage("Touch and hold tag against phone to write.");
		beginWrite();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopWrite();
	}
	
	@Override
    public void onNewIntent(Intent intent) {
		if(mInWriteMode) {
			mInWriteMode = false;
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tag);
		}
    }
	
	private void beginWrite() {
		mInWriteMode = true;
		
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };
        
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	
	private void stopWrite() {
		nfcAdapter.disableForegroundDispatch(this);
	}
	
	private boolean writeTag(Tag tag) {
		byte[] payload = "Text stored in an NFC tag".getBytes();
		byte[] mimeBytes = "text/plain".getBytes(Charset.forName("US-ASCII"));
        NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
		NdefMessage message = new NdefMessage(new NdefRecord[] { cardRecord });
        
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					displayMessage("This is a read-only tag");
					return false;
				}
				
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size) {
					displayMessage("There is not enough space to write");
					return false;
				}

				ndef.writeNdefMessage(message);
				displayMessage("Write successful.");
				return true;
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						displayMessage("Write successful\nLaunch a scanning app or scan and choose to read.");
						return true;
					} catch (IOException e) {
						displayMessage("Unable to format tag to NDEF.");
						return false;
					}
				} else {
					displayMessage("Tag doesn't appear to support NDEF format.");
					return false;
				}
			}
		} catch (Exception e) {
			displayMessage("Write failed");
		}

        return false;
    }
	
	private void displayMessage(String message) {
		Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
	}

}

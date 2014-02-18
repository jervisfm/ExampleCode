package com.cookbook.nfcreader;

import com.cookbook.nfcreader.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected NfcAdapter nfcAdapter;
	protected PendingIntent nfcPendingIntent;
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }
    
    public void enableForegroundMode() {
		Log.d(TAG, "enableForegroundMode");

		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED); // filter for all
		IntentFilter[] writeTagFilters = new IntentFilter[] {tagDetected};
		nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, writeTagFilters, null);
	}
    
    public void disableForegroundMode() {
		Log.d(TAG, "disableForegroundMode");

		nfcAdapter.disableForegroundDispatch(this);
	}
    
    @Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent");
		String stringOut = "";

		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {			
			TextView textView = (TextView) findViewById(R.id.main_tv);

			Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (messages != null) {
				for (int i = 0; i < messages.length; i++) {
					NdefMessage message = (NdefMessage)messages[i];
					NdefRecord[] records = message.getRecords();
	
					for (int j = 0; j < records.length; j++) {
						NdefRecord record = records[j];
						// need to build an append instead of an overwrite!
//						String consoleName = new String(records[j].getPayload());
//						textView.setText(consoleName);
						
						stringOut += "TNF: " + record.getTnf() + "\n";
						stringOut += "MIME Type: " + new String(record.getType()) + "\n";
						stringOut += "Payload: " + new String(record.getPayload()) + "\n\n";
						textView.setText(stringOut);
					}
			    }
			}
		}
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");

		super.onResume();

		enableForegroundMode();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");

		super.onPause();

		disableForegroundMode();
	}
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    */
    
}

package com.androidtablet.audiorecordapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.provider.MediaStore;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import android.net.Uri;

public class AudioRecordAppActivity extends Activity {
	final int RECORD_AUDIO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_audio_record_app);
	Button captureBtn = (Button) findViewById(R.id.record_btn);		
	captureBtn.setOnClickListener(new Button.OnClickListener() { 
	    public void onClick(View v) {
	    	try {
				Intent audioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
					startActivityForResult(audioIntent, RECORD_AUDIO);	 
				}
			catch(ActivityNotFoundException anfe){
				Toast.makeText(AudioRecordAppActivity.this, "Error occurred while audio recording", Toast.LENGTH_SHORT).show();
				}
	    } 
	});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	if (resultCode == RESULT_OK) {		
		if(requestCode == RECORD_AUDIO){
			Uri audioUri = intent.getData();	
		}
	}
	}

}



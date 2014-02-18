package com.androidtablet.audiocaptureapp;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.AudioEncoder;
import java.io.File;
import android.os.Environment;

public class AudioCaptureAppActivity extends Activity {
	private MediaRecorder mediaRecorder = null;
	private File audioFile = null;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_capture_app);
	     textView = (TextView)findViewById(R.id.textview);
	         audioFile = new File(Environment.getExternalStorageDirectory(), "testaudio.3gp");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_audio_capture_app, menu);
		return true;
	}

	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.start_recording_button: 
startRecording(audioFile);
	                break;
	            case R.id.stop_recording_button: 
	            	stopRecording();
	                break;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }
	
	  private void startRecording(File file) {	 
		  if (mediaRecorder != null) {	 
		  mediaRecorder.release();	 
		  }	 
		  mediaRecorder = new MediaRecorder();	 
		  mediaRecorder.setAudioSource(AudioSource.MIC);	 
		  mediaRecorder.setOutputFormat(OutputFormat.THREE_GPP);	 
		  mediaRecorder.setAudioEncoder(AudioEncoder.AMR_WB);	 
		  mediaRecorder.setOutputFile(file.getAbsolutePath());	 
		  try {	 
		  mediaRecorder.prepare();	 
		  mediaRecorder.start();	 
		  textView.setText("Recording started. Press Stop Recording button to stop");
		  } catch (IOException e) {	 
	e.printStackTrace();
		  }	 
		  }	 

	  private void stopRecording() {	 
		  if (mediaRecorder != null) {	 
		  mediaRecorder.stop();	 
		  mediaRecorder.release();	 
		  mediaRecorder = null;	 
		  textView.setText("Audio recorded");
		  }	 
		  }	
}

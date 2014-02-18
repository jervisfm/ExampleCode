package com.androidtablet.videorecordapp;


import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.provider.MediaStore;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import android.net.Uri;
import android.widget.VideoView;

public class VideoRecordAppActivity extends Activity {
	VideoView videoView;
	final int  RECORD_VIDEO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_video_record_app);
	Button videoCaptureBtn = (Button) findViewById(R.id.record_video_btn);		
	videoView = (VideoView) findViewById(R.id.videoview);		
	videoCaptureBtn.setOnClickListener(new Button.OnClickListener() { 
	    public void onClick(View v) {
	    	try {
				Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
					startActivityForResult(videoIntent,  RECORD_VIDEO);	 
				}
			catch(ActivityNotFoundException anfe){
				Toast.makeText(VideoRecordAppActivity.this, "Error occurred while video recording", Toast.LENGTH_SHORT).show();
				}
	    } 
	});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	if (resultCode == RESULT_OK) {	
        if(requestCode == RECORD_VIDEO ){
			Uri videoUri = intent.getData();	
			  videoView.setVideoURI(videoUri);
			  videoView.start();
        }
	}
	}
}



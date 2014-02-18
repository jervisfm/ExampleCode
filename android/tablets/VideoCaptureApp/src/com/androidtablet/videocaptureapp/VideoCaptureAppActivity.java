package com.androidtablet.videocaptureapp;

import java.io.IOException;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import android.os.Environment;
import android.media.MediaRecorder.OnInfoListener;
import android.media.MediaRecorder.OnErrorListener;

public class VideoCaptureAppActivity extends Activity implements SurfaceHolder.Callback, OnInfoListener, OnErrorListener {
	  private MediaRecorder mediaRecorder;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private String outputFile;

	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);	
	      mediaRecorder = new MediaRecorder();
	      mediaRecorder.setOnInfoListener(this);
	      mediaRecorder.setOnErrorListener(this);
   initMediaRecorder();
	      setContentView(R.layout.activity_video_capture_app);
	        surfaceView = (SurfaceView) findViewById(R.id.videoview);
		      surfaceHolder = surfaceView.getHolder();
		       surfaceHolder.addCallback(this);         
	  }

@Override
	  protected void onPause() {
	      super.onPause();
	      releaseMediaRecorder();      
	  }
  
  @Override
  protected void onResume() {
    super.onResume();
  }
    
  @Override
  protected void onDestroy() {
      super.onDestroy();
  }
  
	  private void releaseMediaRecorder(){
	      if (mediaRecorder != null) {
	          mediaRecorder.reset();   
	          mediaRecorder.release(); 
	          mediaRecorder = null;     
	      }
	  }

	  @Override
	  public void onInfo(MediaRecorder mr, int what, int extra) {
	    if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
	    	 try {
	    	mediaRecorder.stop();
	    	  }
	          catch(IllegalStateException e) {
	          }
	    	 releaseMediaRecorder();
	      Toast.makeText(this, "Exceeded the Recording limit. Stopping the recording", Toast.LENGTH_SHORT).show();
	      finish();
	    }
	  }

	  @Override
	  public void onError(MediaRecorder mr, int what, int extra) {
		  try {
		  mediaRecorder.stop();
		  }
          catch(IllegalStateException e) {
          }
		  releaseMediaRecorder();
	    Toast.makeText(this, "Error occurred in Recording. Stopping the recording", Toast.LENGTH_SHORT).show();
	     finish();
	  }
	  
		private void initMediaRecorder(){
			 outputFile = Environment.getExternalStorageDirectory().getPath() + "/myvideo.mp4";
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
	   mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
  /*      mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setVideoFrameRate(10);
           mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
           mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);*/
 mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
          mediaRecorder.setOutputFile(outputFile);
  mediaRecorder.setMaxDuration(10000); 
          mediaRecorder.setMaxFileSize(5000000); 
 }    
 
		private void prepareMediaRecorder() {
  mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
  try {    
	       mediaRecorder.prepare();
	   } catch (IllegalStateException e) {
	   e.printStackTrace();
	   finish();
	   } catch (IOException e) {
	   e.printStackTrace();
	   finish();
	   }
	}
	
@Override
	public void surfaceCreated(SurfaceHolder holder) {
	   prepareMediaRecorder();
    mediaRecorder.start();   
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int weight,  int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	}


package com.androidtablet.camcorderapp;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class CamcorderAppActivity extends Activity {
	private static final int QUALITY_LOW = 0;
	private static final int QUALITY_HIGH = 1;
	 TextView textProfile;
	 
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_camcorder_app);
	       textProfile = (TextView)findViewById(R.id.profile);
	   }
	  
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_camcorder_app, menu);                              
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.high_quality_profile: 
	            	ProfileInfo(QUALITY_HIGH);                
	                break;
	            case R.id.low_quality_profile: 
	            	ProfileInfo(QUALITY_LOW);                       
	                break;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }

	    private void ProfileInfo(int quality_type)
	    {
	    	CamcorderProfile camcorderProfile = CamcorderProfile.get(quality_type);
	    	String selectedProfile="";
		   String profileInfo;
		   if(quality_type==QUALITY_HIGH) selectedProfile="QUALITY_HIGH";
		   else 
			   if(quality_type==QUALITY_LOW) selectedProfile="QUALITY_LOW";
		   profileInfo = selectedProfile + " : \n" + 
			   camcorderProfile.toString() +"\n";
		   profileInfo +=  "AudioBitRate: " + String.valueOf(camcorderProfile.audioBitRate) +"\n"
		     + "AudioChannels: " + String.valueOf(camcorderProfile.audioChannels) +"\n"
		     + "AudioCodec: " + AudioCodecinString(camcorderProfile.audioCodec) +"\n"
		     + "AudioSampleRate: " + String.valueOf(camcorderProfile.audioSampleRate) +"\n"
		     + "Duration: " + String.valueOf(camcorderProfile.duration) +"\n"
		     + "FileFormat: " + FileFormatinString(camcorderProfile.fileFormat) +"\n"
		     + "Quality: " + String.valueOf(camcorderProfile.quality) +"\n"
		     + "VideoBitRate: " + String.valueOf(camcorderProfile.videoBitRate) +"\n"
		     + "VideoCodec: " + VideoCodecinString(camcorderProfile.videoCodec) +"\n"
		     + "VideoFrameRate: " + String.valueOf(camcorderProfile.videoFrameRate) +"\n"
		     + "VideoFrameWidth: " + String.valueOf(camcorderProfile.videoFrameWidth) +"\n"
		     + "VideoFrameHeight: " + String.valueOf(camcorderProfile.videoFrameHeight);   
		   
		   textProfile.setText(profileInfo);
	    }
	     
	 private String AudioCodecinString(int audioCodec){
	  switch(audioCodec){
	   case AudioEncoder.AAC:
	    return "AAC";
	   case AudioEncoder.AAC_ELD:
	    return "AAC_ELD";
	   case AudioEncoder.AMR_NB:
		    return "AMR_NB";
		   case AudioEncoder.AMR_WB:
		    return "AMR_WB";
		   case AudioEncoder.DEFAULT:
			    return "DEFAULT";
			   case AudioEncoder.HE_AAC:
			    return "HE_AAC";
	   default:
	    return "unknown";
	  }  
	 }
	 
	 private String FileFormatinString(int fileFormat){
	  switch(fileFormat){
	   case OutputFormat.AAC_ADTS:
	    return "AAC_ADTS";
	   case OutputFormat.AMR_NB:
	    return "AMR_NB";
	   case OutputFormat.AMR_WB:
	    return "AMR_WB";
	   case OutputFormat.DEFAULT:
	    return "DEFAULT";
	   case OutputFormat.MPEG_4:
		    return "MPEG_4";
		   case OutputFormat.THREE_GPP:
		    return "THREE_GPP";
	   default:
	    return "unknown";
	  }  
	 }
	 
	 private String VideoCodecinString(int videoCodec){
	  switch(videoCodec){
	   case VideoEncoder.H263:
	    return "H263";
	   case VideoEncoder.H264:
	    return "H264";
	   case VideoEncoder.MPEG_4_SP:
	    return "MPEG_4_SP";
	   case VideoEncoder.DEFAULT:
	    return "DEFAULT";
	   default:
	    return "unknown";
	  }  
	 }
}

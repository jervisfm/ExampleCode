package com.androidtablet.captureimageapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import java.io.File;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaptureImageAppActivity extends Activity implements SurfaceHolder.Callback {
	  private SurfaceHolder surfaceHolder; 
	  private SurfaceView surfaceView;
	  private Camera camera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_image_app);
		  surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
          surfaceHolder = surfaceView.getHolder();
		  surfaceHolder.addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_capture_image_app, menu);
		return true;
	}

	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.capture_button: 
	      			  camera.takePicture(null, null, picHandler);        		
	                break;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }

	  PictureCallback picHandler = new PictureCallback() {	 
		  @Override	 
		  public void onPictureTaken(byte[] data, Camera camera) {	
			  File pictureFile = new File(Environment.getExternalStorageDirectory().getPath() + "/sampleimage.jpg");
		/*	  Calendar currentDate = Calendar.getInstance();
		       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(currentDate.getTime());
		       File pictureFile = new File(Environment.getExternalStorageDirectory().getPath() + "/IMG_" + timeStamp + ".jpg");*/
		  try {	 
		  FileOutputStream fos = new FileOutputStream(pictureFile.toString());	 
		    fos.write(data);	
		  fos.close();	 
          Toast.makeText(CaptureImageAppActivity.this, "Picture Saved", Toast.LENGTH_SHORT).show();
		  } catch (FileNotFoundException e) {	 
		  } catch (IOException e) {	 
		  }	 
		  }	 
		  };	 
	  
	  public void surfaceCreated(SurfaceHolder holder) {
			camera=Camera.open();		
		  try {
	            camera.setPreviewDisplay(holder);
	            camera.startPreview();
	        } catch (IOException e) {
	        }
		  }

	  
	  public void surfaceChanged(SurfaceHolder holder,  int format, int width, int height) {
		  try {
	            camera.setPreviewDisplay(holder);
	           camera.startPreview();
	        } catch (Exception e) {   }
			  }
  
	  public void surfaceDestroyed(SurfaceHolder holder) {  
		  camera.stopPreview();  
		  camera.release();  
		  camera = null;  
		  }
}

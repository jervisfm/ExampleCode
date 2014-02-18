package com.androidtablet.cameraapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.provider.MediaStore;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.widget.ImageView;

	public class CameraAppActivity extends Activity {
		final int CAPTURE_PHOTO = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_app);
		Button captureBtn = (Button) findViewById(R.id.launch_btn);		
		captureBtn.setOnClickListener(new Button.OnClickListener() { 
            public void onClick(View v) {
            	try {
    				Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     				startActivityForResult(captureIntent, CAPTURE_PHOTO);
    				}
    			catch(ActivityNotFoundException e){
    				Toast.makeText(CameraAppActivity.this, "Error occurred while capturing image", Toast.LENGTH_SHORT).show();
    				}
            } 
        });
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {		
			if(requestCode == CAPTURE_PHOTO){
			    Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			    ImageView picView = (ImageView)findViewById(R.id.photo);
				picView.setImageBitmap(photo);
			}
		}
		else
			Toast.makeText(CameraAppActivity.this, "Image could not be captured", Toast.LENGTH_SHORT).show();
		}
}

package com.androidtablet.btfiletransferapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Environment;
import java.io.File;
import android.content.Intent;
import android.net.Uri;

public class BTFileTransferAppActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_btfile_transfer_app);
        Button transferFileButton = (Button) findViewById(R.id.transfer_file_button);
        transferFileButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String filePath = Environment.getExternalStorageDirectory().toString() + "/sampleimage.jpg";
            	File file = new File(filePath);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(intent);
            }
        });		
	}
}

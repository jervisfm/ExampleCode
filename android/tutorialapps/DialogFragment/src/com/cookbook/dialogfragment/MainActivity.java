package com.cookbook.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button buttonOpenDialog = (Button) findViewById(R.id.opendialog);
		buttonOpenDialog.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				OpenDialog();
			}
		});

	}

	void OpenDialog() {
		MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
		myDialogFragment.show(getSupportFragmentManager(), "myDialogFragment");
	}

	public void protestClicked() {
		Toast.makeText(MainActivity.this, "Your protest has been recorded", Toast.LENGTH_LONG).show();
	}

	public void forgetClicked() {
		Toast.makeText(MainActivity.this, "You have chosen to forget", Toast.LENGTH_LONG).show();
	}

}

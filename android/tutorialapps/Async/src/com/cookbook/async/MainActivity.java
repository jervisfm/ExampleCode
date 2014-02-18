package com.cookbook.async;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mainTextView;
	ProgressBar mainProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainTextView = (TextView) findViewById(R.id.mainTextView);
		mainProgress = (ProgressBar) findViewById(R.id.mainProgress);
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... parameter) {
			String result = "";

			Pattern pattern = Pattern.compile("meow");
			Matcher matcher = pattern.matcher(parameter[0]);
			
			int count = 0;
			while (matcher.find()){
				count++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// remember to error handle
				}
				publishProgress(count + 20);
			}
			
			result = "meow was found "+count+" times";
			
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mainProgress.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			mainTextView.setText(result);
		}
	}

	public void executeAsync(View view) {
		MyAsyncTask task = new MyAsyncTask();
		task.execute("Meow, meow, meow many times do you have meow?");
	}

}

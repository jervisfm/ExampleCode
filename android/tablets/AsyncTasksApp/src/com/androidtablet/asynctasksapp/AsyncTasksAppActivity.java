package com.androidtablet.asynctasksapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.AsyncTask;

public class AsyncTasksAppActivity extends Activity {
    TextView seqNums;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_tasks_app);
        seqNums=(TextView) findViewById(R.id.seqnums);
        new PrintSequenceTask().execute(1);
    }

    private class PrintSequenceTask extends 
        AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            seqNums.setText("Sequence numbers begins");
        }

        @Override
        protected Void doInBackground(Integer... args) {
            for (int i = args[0]; i <= 10; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
           seqNums.setText(args[0].toString());
        } 

        @Override
        protected void onPostExecute(Void result) {
           seqNums.setText("Sequence numbers over");
        } 
    } 
}

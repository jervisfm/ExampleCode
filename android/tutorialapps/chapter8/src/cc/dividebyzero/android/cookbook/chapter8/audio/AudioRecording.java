package cc.dividebyzero.android.cookbook.chapter8.audio;

import cc.dividebyzero.android.cookbook.chapter8.R;
import cc.dividebyzero.android.cookbook.chapter8.R.id;
import cc.dividebyzero.android.cookbook.chapter8.R.layout;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioRecording extends Activity implements Runnable {   
    private TextView statusText;  

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_recording);   

        statusText = (TextView) findViewById(R.id.status);

        Button actionButton = (Button) findViewById(R.id.record);
        actionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                record_thread();
            }
        });

        Button replayButton = (Button) findViewById(R.id.play);
        replayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Thread thread = new Thread(AudioRecording.this);
                thread.start();
            }
        });
    }

    String text_string;
    final Handler mHandler = new Handler();  
    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi(text_string);
        }
    };
    
    private void updateResultsInUi(String update_txt) {
        statusText.setText(update_txt);
    }

    private void record_thread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                text_string = "Starting";
                mHandler.post(mUpdateResults);

                record();

                text_string = "Done";
                mHandler.post(mUpdateResults);
            }     
        });
        thread.start();
    }

    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    int frequency = 11025; //Hz
    int bufferSize = 50*AudioTrack.getMinBufferSize(
    		frequency, 
            AudioFormat.CHANNEL_OUT_MONO, 
            audioEncoding
            );    
    // Create new AudioRecord object to record the audio. 
    public AudioRecord audioRecord = new AudioRecord(
            MediaRecorder.AudioSource.MIC, 
            frequency, 
            AudioFormat.CHANNEL_IN_MONO, 
            audioEncoding, 
            bufferSize
            );
    // Create new AudioTrack object w/same parameters as AudioRecord obbj
    public AudioTrack audioTrack = new AudioTrack(
            AudioManager.STREAM_MUSIC, 
            frequency, 
            AudioFormat.CHANNEL_OUT_MONO, 
            audioEncoding, 
            4096, 
            AudioTrack.MODE_STREAM
            );
    short[] buffer = new short[bufferSize];

    public void record() {
        try {
            audioRecord.startRecording();
            audioRecord.read(buffer, 0, bufferSize);
            audioRecord.stop();
        } catch (Throwable t) {
            Log.e("AudioExamplesRaw","Recording Failed");
        }   
    }

    public void run() { //play audio using runnable Activity
        audioTrack.play();
        int i=0;
        while(i<bufferSize) {
            audioTrack.write(buffer, i++, 1);
        }
        return;
    }
    
    @Override
    protected void onPause() {        
        if(audioTrack!=null) {
            if(audioTrack.getPlayState()==AudioTrack.PLAYSTATE_PLAYING) {
                audioTrack.pause();
            }
        }
        super.onPause();
    }
}

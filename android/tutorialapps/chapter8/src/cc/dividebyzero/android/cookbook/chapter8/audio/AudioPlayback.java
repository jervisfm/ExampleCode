package cc.dividebyzero.android.cookbook.chapter8.audio;

import cc.dividebyzero.android.cookbook.chapter8.ListFiles;
import cc.dividebyzero.android.cookbook.chapter8.R;
import cc.dividebyzero.android.cookbook.chapter8.R.id;
import cc.dividebyzero.android.cookbook.chapter8.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class AudioPlayback extends Activity {
    static final String MUSIC_DIR = "/music/";
    Button playPauseButton;

    private MediaPlayer m_mediaPlayer; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.audio_playback); 
        playPauseButton = (Button) findViewById(R.id.play_pause);
        
        m_mediaPlayer= new MediaPlayer(); 

        String musicDir = Environment.getExternalStorageDirectory()
        .getAbsolutePath() + MUSIC_DIR;

        //Show a list of Music files to choose
        Intent i = new Intent(this, ListFiles.class);
        i.putExtra("directory", musicDir);
        startActivityForResult(i,0);     

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(m_mediaPlayer.isPlaying()) {
                    //stop and give option to start again
                    pauseMP();
                } else {
                    startMP();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode==RESULT_OK) {
            String tmp = data.getExtras().getString("clickedFile"); 

            try {
                m_mediaPlayer.setDataSource(tmp);                
                m_mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();            
            }
            startMP();
        }
    }

    void pauseMP() {
        playPauseButton.setText("Play");
        m_mediaPlayer.pause();        
    }

    void startMP() {                 
        m_mediaPlayer.start();            
        playPauseButton.setText("Pause");     
    }

    boolean needToResume = false;
    @Override
    protected void onPause() {
        if(m_mediaPlayer != null && m_mediaPlayer.isPlaying()) {
            needToResume = true;  
            pauseMP();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needToResume && m_mediaPlayer != null) {  
            startMP();
        }
    }
}

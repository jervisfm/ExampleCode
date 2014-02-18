package cc.dividebyzero.android.cookbook.chapter8.video;

import java.io.File;

import cc.dividebyzero.android.cookbook.chapter8.ListFiles;
import cc.dividebyzero.android.cookbook.chapter8.R;
import cc.dividebyzero.android.cookbook.chapter8.R.id;
import cc.dividebyzero.android.cookbook.chapter8.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class VideoPlayback extends Activity {
	private static final String VIDEO_DIR = File.separator+"DCIM"+File.separator+"Camera";
	protected static final int MSG_PLAY = 1;
    Button playPauseButton;

    private MediaPlayer m_mediaPlayer;

	private SurfaceView mVideoSurface;
	protected boolean mIsPrepared;
	protected boolean mSurfaceCreated; 
	
	private Handler handler=new Handler(new Handler.Callback() {
		
		public boolean handleMessage(Message msg) {
			if(msg.what==MSG_PLAY){
				if(mSurfaceCreated){
					startMP();
				}else{
					handler.sendEmptyMessageDelayed(MSG_PLAY, 300);
				}
			}
			return false;
		}
	});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_playback); 
        playPauseButton = (Button) findViewById(R.id.play_pause);
        mVideoSurface=(SurfaceView)findViewById(R.id.surface);
        SurfaceHolder holder =  mVideoSurface.getHolder();
        holder.setSizeFromLayout();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mVideoSurface.setVisibility(View.VISIBLE);
        holder.addCallback(new Callback() {
			
			public void surfaceDestroyed(SurfaceHolder holder) {
				m_mediaPlayer.setDisplay(null);
				
			}
			
			public void surfaceCreated(SurfaceHolder holder) {
				android.util.Log.v("VP","surf created");				
				mSurfaceCreated=true;

			}
			
			public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {

				
			}
		});
        m_mediaPlayer= new MediaPlayer();
        m_mediaPlayer.setOnErrorListener(new OnErrorListener() {
			
			public boolean onError(MediaPlayer mp, int what, int extra) {
				android.util.Log.e("MP","Error="+what);
				return false;
			}
		});
        m_mediaPlayer.setDisplay(holder);		
        

        
        
        String MusicDir = Environment.getExternalStorageDirectory()
        .getAbsolutePath() + VIDEO_DIR;

        //Show a list of Music files to choose
        Intent i = new Intent(this, ListFiles.class);
        i.putExtra("directory", MusicDir);
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
                handler.sendEmptyMessage(MSG_PLAY);
            } catch (Exception e) {
                e.printStackTrace();            
            }
        }
    }


    
    void pauseMP() {
        playPauseButton.setText("Play");
        m_mediaPlayer.pause();        
    }

    void startMP() {                
    	android.util.Log.v("MP","start");
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

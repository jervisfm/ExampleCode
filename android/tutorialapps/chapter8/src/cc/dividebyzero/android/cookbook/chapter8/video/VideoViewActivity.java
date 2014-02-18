package cc.dividebyzero.android.cookbook.chapter8.video;

import java.io.File;

import cc.dividebyzero.android.cookbook.chapter8.ListFiles;
import cc.dividebyzero.android.cookbook.chapter8.R;
import cc.dividebyzero.android.cookbook.chapter8.R.id;
import cc.dividebyzero.android.cookbook.chapter8.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;



public class VideoViewActivity extends Activity {

	
	private static final String VIDEO_DIR = File.separator+"DCIM"+File.separator+"Camera";
	private VideoView videoView;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.video_view);
		videoView=(VideoView)findViewById(R.id.videoView1);
		MediaController controller=new MediaController(this);
		controller.setMediaPlayer(videoView);
		controller.setAnchorView(videoView);

		videoView.setMediaController(controller);

		
       String videoDir = Environment.getExternalStorageDirectory()
        .getAbsolutePath() + VIDEO_DIR;

        //Show a list of Video files to choose
        Intent i = new Intent(this, ListFiles.class);
        i.putExtra("directory", videoDir);
        startActivityForResult(i,0);     
		
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode==RESULT_OK) {
            String path = data.getExtras().getString("clickedFile"); 
	        videoView.setVideoPath(path);
	        videoView.start();
           
        }
    }
    

	
}

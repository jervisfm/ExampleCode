package com.cookbook.asynctaskdemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.cookbook.asynctaskdemo.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final int LOADER_ID = 1;
	SimpleCursorAdapter mAdapter;
	ImageView mImageView;
	
    static final String[] CONTACTS_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME
    };
	private static final String IMAGE_URL = "http://developer.android.com/images/brand/Android_Robot_100.png";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageView=(ImageView)findViewById(R.id.image);
		new ImageLoaderTask(getApplicationContext()).execute(IMAGE_URL);
		
		
	}


	public class ImageLoaderTask extends AsyncTask<String, Void, String>{

		private Context context;

		public ImageLoaderTask(Context context){
			this.context=context;
		}

		@Override
		protected String doInBackground(String... params) {
			String path=context.getFilesDir()+File.pathSeparator+"temp_"+System.currentTimeMillis()+".png";
			HttpURLConnection connection=null;
			
			android.util.Log.v("TASK","opening url="+params[0]);
			
		    try {
		    	final URL url=new URL(params[0]);
		    	connection=(HttpURLConnection) url.openConnection();
			    InputStream in = new BufferedInputStream(connection.getInputStream());
			    
			    OutputStream out= new FileOutputStream(path);
		        int data = in.read();
		        while (data != -1) {
		        	out.write(data);		            
		            data = in.read();    
		        }
		        
		    } catch (IOException e) {
		    	android.util.Log.e("TASK","error loading image",e);
			   e.printStackTrace();		   
			   return null;
			}finally {
				if(connection!=null){
					connection.disconnect();
				}
			}
			return path;
		}

		@Override
		protected void onPostExecute(String imagePath) {
			super.onPostExecute(imagePath);
			if(imagePath!=null){
				android.util.Log.v("TASK","loading image from temp file"+imagePath);
				Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
				mImageView.setImageBitmap(bitmap);
			}
		}

		
	}


}

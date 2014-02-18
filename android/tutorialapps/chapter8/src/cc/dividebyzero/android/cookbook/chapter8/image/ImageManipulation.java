package cc.dividebyzero.android.cookbook.chapter8.image;

import cc.dividebyzero.android.cookbook.chapter8.ListFiles;
import cc.dividebyzero.android.cookbook.chapter8.R;
import cc.dividebyzero.android.cookbook.chapter8.R.id;
import cc.dividebyzero.android.cookbook.chapter8.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

public class ImageManipulation extends Activity {
    static final String CAMERA_PIC_DIR = "/DCIM/Camera/";
    ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_manipulation);
        iv = (ImageView) findViewById(R.id.my_image);

        String ImageDir = Environment.getExternalStorageDirectory().getAbsolutePath() 
        + CAMERA_PIC_DIR;

        Intent i = new Intent(this, ListFiles.class);
        i.putExtra("directory", ImageDir);
        startActivityForResult(i,0);        
    }

    @Override
    protected void onActivityResult(int requestCode,
            int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode==RESULT_OK) {
            String tmp = data.getExtras().getString("clickedFile");
            Bitmap ImageToChange= BitmapFactory.decodeFile(tmp);  
            process_image(ImageToChange);
        }

    }

    void process_image(Bitmap image) {
        Bitmap bm = Bitmap.createScaledBitmap(image, 480, 320, false); 
        int width = bm.getWidth();
        int height = bm.getHeight();
        int x= width>>1; 
        int y= height>>1;
        int[] pixels1 = new int[(width*height)];
        int[] pixels2 = new int[(width*height)];
        int[] pixels3 = new int[(width*height)];
        int[] pixels4 = new int[(width*height)];
        bm.getPixels(pixels1, 0, width, 0, 0, width>>1, height>>1);
        bm.getPixels(pixels2, 0, width, x, 0, width>>1, height>>1);
        bm.getPixels(pixels3, 0, width, 0, y, width>>1, height>>1);
        bm.getPixels(pixels4, 0, width, x, y, width>>1, height>>1);
        if(bm.isMutable()) {
            bm.setPixels(pixels2, 0, width, 0, 0, width>>1, height>>1);
            bm.setPixels(pixels4, 0, width, x, 0, width>>1, height>>1);
            bm.setPixels(pixels1, 0, width, 0, y, width>>1, height>>1);
            bm.setPixels(pixels3, 0, width, x, y, width>>1, height>>1);
        }
        iv.setImageBitmap(bm);
    }
}
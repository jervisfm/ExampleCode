package cc.dividebyzero.android.cookbook.chapter8.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageScaling {

	
	
	
	public Bitmap scaleBitmapByCommonFactor(String sourcePath,int scaleFactor){
		
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		
		BitmapFactory.decodeFile(sourcePath, options);
		int targetHeight=options.outHeight*scaleFactor;
		int targetWidth=options.outWidth*scaleFactor;
		
		options.inJustDecodeBounds=false;

		//BitmapFactory.decodeFile(sourcePath, targetOptions);
		
		
		return null;
	}
	
	
	public Bitmap scaleBitmapToBestFit(String sourcePath,int targetWidth,int targetHeight){
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		
		BitmapFactory.decodeFile(sourcePath, options);
		float scaleFactor=1;
		
		if(options.outHeight>options.outWidth){
			scaleFactor=targetHeight/options.outHeight;
		}else if (options.outHeight<options.outWidth){
			scaleFactor=targetWidth/options.outWidth;
		}
		
		Bitmap src=BitmapFactory.decodeFile(sourcePath);
		return Bitmap.createScaledBitmap(src, (int)(targetWidth*scaleFactor), (int)(targetHeight*scaleFactor), true);

	}
}

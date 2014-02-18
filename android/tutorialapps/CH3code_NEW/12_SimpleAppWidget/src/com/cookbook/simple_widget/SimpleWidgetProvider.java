package com.cookbook.simple_widget;

import java.io.File;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

public class SimpleWidgetProvider extends AppWidgetProvider { 
    final static int APPWIDGET = 1001;
    
    @Override
    public void onUpdate(Context context,
            AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // Loop through all widgets to display an update
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            String titlePrefix = "AppWidget #";
            updateAppWidget(context, appWidgetManager, appWidgetId, 
                            titlePrefix);
        }
    }
    static int imageNum=0;
    static void updateAppWidget(Context context, AppWidgetManager 
            appWidgetManager, int appWidgetId, String titlePrefix) {    
        String ImageDir = Environment.getExternalStorageDirectory().getAbsolutePath() 
        + "/DCIM/Camera/";
        File directory = new File(ImageDir);
        
        File[] files = directory.listFiles();
        
       // CharSequence text = titlePrefix + files[imageNum++];

        // Construct the RemoteViews object.  
        RemoteViews views = new RemoteViews(context.getPackageName(), 
                R.layout.widget_layout);
        Log.e("DD",""+Uri.fromFile(files[imageNum]));
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap ImageToChange= BitmapFactory.decodeFile(files[imageNum++].toString());  
        Bitmap bm = Bitmap.createScaledBitmap(ImageToChange, 146, 72, false); 
      
        views.setImageViewBitmap(R.id.widget_example_image, bm);
    

        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}    

package com.androidtablet.surfaceviewapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class SurfaceViewAppActivity extends Activity {
    MySurfaceView mySurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
        protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
        protected void onPause() {
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }

    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private MySurfaceViewThread mySurfaceViewThread;
        private boolean hasSurface;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Bitmap bitmap ;
        Point pt1 = new Point();
        Point pt2 = new Point();
        private boolean drawing;

        public MySurfaceView(Context context) {
            super(context);
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            holder = getHolder();                 
            holder.addCallback(this);              
            hasSurface = false;
        }
        public void onResumeMySurfaceView(){
            if (mySurfaceViewThread == null) {      
                mySurfaceViewThread = new MySurfaceViewThread();
                if (hasSurface == true)
                    mySurfaceViewThread.start();   
            }
        }
        public void onPauseMySurfaceView(){
            boolean retry = true;
            while(retry){
                try {
                    mySurfaceViewThread.join();       
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void surfaceCreated(SurfaceHolder holder) {  
            hasSurface = true;
            if (mySurfaceViewThread != null)
                mySurfaceViewThread.start();
        }
        public void surfaceDestroyed(SurfaceHolder holder) {  
            hasSurface = false;
            onPauseMySurfaceView();
        }
        public void surfaceChanged(SurfaceHolder holder, int  
            format, int w, int h) {                         
            if (mySurfaceViewThread != null)
                mySurfaceViewThread.onWindowResize(w, h);
        }
               @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) 
            { 
                pt1.x = (int) event.getX();
                pt1.y = (int) event.getY();
                drawing = false;
            } 
            else if (event.getAction() == MotionEvent.ACTION_UP) {                                 
                pt2.x = (int) event.getX();
                pt2.y = (int) event.getY();
                drawing = true;
            }
            return true;
        }
        @Override
        public void onDraw(Canvas canvas) {      
            paint.setStrokeWidth(5);
            paint.setColor(Color.RED);         
            int imageWidth=(int) getResources().getDimension(R.dimen.image_width);
            int imageHeight=(int)getResources().getDimension(R.dimen.image_height);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, true), 50, 50, null);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;           
                   float centerX = width / 2;
                   float centerY = height / 2;
                    canvas.drawCircle(centerX,centerY,70,paint); 
            if (drawing){
                canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);                            
            } 
        }

        class MySurfaceViewThread extends Thread {     
            private boolean done;

            MySurfaceViewThread() {
                super();
                done = false;
            }

            @Override
            public void run() {                          
                SurfaceHolder surfaceHolder = holder;
                while (!done) {
                 Canvas canvas = surfaceHolder.lockCanvas(); 
                    onDraw(canvas);                          
                    if(canvas !=null)
                  surfaceHolder.unlockCanvasAndPost(canvas); 
                }
            }

            public void requestExitAndWait() {
                done = true;
                try {
                    join();
                } catch (InterruptedException ex) { }
            }
            public void onWindowResize(int w, int h) { }
        }
    }
}

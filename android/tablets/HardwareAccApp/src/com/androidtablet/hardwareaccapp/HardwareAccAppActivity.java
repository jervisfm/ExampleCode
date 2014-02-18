package com.androidtablet.hardwareaccapp;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.graphics.Color;

public class HardwareAccAppActivity extends Activity {
    Paint paint = new Paint();  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView=new MyView(this);
        setContentView(myView);     
    }

    public class MyView extends View{  
        public MyView(Context context){
            super(context);        
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.RED); 
            paint.setTextSize(getResources().getDimension(R.dimen.text_size));
            if(canvas.isHardwareAccelerated())
                canvas.drawText("Canvas is Hardware Accelerated", 10, 75, paint); 
            else
                canvas.drawText("Canvas is not Hardware Accelerated", 10, 75, paint);             
        }
    }
}

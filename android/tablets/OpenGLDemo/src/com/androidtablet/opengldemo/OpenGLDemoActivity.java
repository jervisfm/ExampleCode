package com.androidtablet.opengldemo;

import android.os.Bundle;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OpenGLDemoActivity extends Activity {
    private GLSurfaceView myGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new GLSurfaceView(this);
        MyGLSurfRenderer renderer=new MyGLSurfRenderer();
        myGLView.setRenderer(renderer); 
        setContentView(myGLView);  
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }

    public class MyGLSurfRenderer implements  GLSurfaceView.Renderer {
        private Rectangle rectangle; 
private float xscale=0.9f;
private float yscale=0.9f;
private float zscale=0.9f;
private float xdelta=0.0f;
private float ydelta=0.0f;

        public MyGLSurfRenderer() {
            rectangle = new Rectangle(); 
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
        	  gl.glLoadIdentity();
        }

        public void onDrawFrame(GL10 gl) {
            gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
       /*     The following commented out code is for rotating the rectangle to 45 degrees
            gl.glPushMatrix();
            gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
            rectangle.draw(gl);
            gl.glPopMatrix();*/
             
         /*  The following commented out code is for rotating the rectangle continuously in anti clockwise direction  
            gl.glRotatef(2.0f, 0.0f, 0.0f, 1.0f);
            rectangle.draw(gl); */
       
            /* The following commented out code is for scaling the rectangle to 50% 
            gl.glPushMatrix();
            gl.glScalef(0.5f,0.5f, 0.5f);
            rectangle.draw(gl);
            gl.glPopMatrix(); */
            
      /* The following commented out code  is for scaling the rectangle to 25% and then enlarging it to its original size      
            if(xscale <=.25) {
                xscale=1;
                yscale=1;
                zscale=1;
            }  
            gl.glPushMatrix();
            gl.glScalef(xscale,yscale, zscale);
            rectangle.draw(gl);
           gl.glPopMatrix();   
            xscale-=.1;
            yscale-=.1;
            zscale-=.1;     */
            
     /* Following code translates the rectangle */       
            if(xdelta >=2)xdelta=0;
            if(ydelta >=2)ydelta=0;
            gl.glPushMatrix();
            gl.glTranslatef(xdelta, ydelta, 0.0f);
            rectangle.draw(gl);
            gl.glPopMatrix();   
            xdelta+=.02;
            ydelta+=.02;
    }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }
    }

    public class Rectangle {
        private FloatBuffer boxBuffer;  
        private FloatBuffer colorBuffer;
        float vertices[] = {
            -0.5f, -0.5f, 0.0f,  
            0.5f, -0.5f,  0.0f, 
            -0.5f,  0.5f,  0.0f,  
            0.5f,  0.5f,  0.0f  
        };
        float[] colors = {  
            0.0f, 0.0f, 1.0f, 1.0f,  
            0.0f, 1.0f, 0.0f, 1.0f,  
            1.0f, 0.0f, 0.0f, 1.0f,  
            0.0f, 0.0f, 1.0f, 1.0f  
        };

        public Rectangle() {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect (vertices.length * 4);
            byteBuffer.order(ByteOrder.nativeOrder()); 
            boxBuffer = byteBuffer.asFloatBuffer(); 
            boxBuffer.put(vertices);        
            boxBuffer.position(0);     
            ByteBuffer colorBytes = ByteBuffer.allocateDirect (colors.length * 4);
            colorBytes.order(ByteOrder.nativeOrder());
            colorBuffer = colorBytes.asFloatBuffer();
            colorBuffer.put(colors);
            colorBuffer.position(0);
        }

        public void draw(GL10 gl) {
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, boxBuffer);
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        }
    }
}

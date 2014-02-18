package com.androidtablet.openglapp;

import android.os.Bundle;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OpenGLAppActivity extends Activity {
    private GLSurfaceView myGLView;

    public void onCreate(Bundle savedInstanceState) {
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

    public class MyGLSurfRenderer implements  
        GLSurfaceView.Renderer {                          
        private FloatBuffer boxBuffer;
        private FloatBuffer colorBuffer;

        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {                                                 
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);      
            defineGraphic();                              
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, boxBuffer);                                
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }

        public void onDrawFrame(GL10 gl) {                 
            gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);       
            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);          
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4); 
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {                                      
            gl.glViewport(0, 0, width, height);          
        }

        private void defineGraphic(){
            float vertices[] = {                           
                -0.5f, -0.5f,  0.0f,  
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

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);                     
            byteBuffer.order(ByteOrder.nativeOrder());
            boxBuffer = byteBuffer.asFloatBuffer();       
            boxBuffer.put(vertices);                     
            boxBuffer.position(0);  
            ByteBuffer colorBytes = ByteBuffer.allocateDirect(colors.length * 4);
            colorBytes.order(ByteOrder.nativeOrder());
            colorBuffer = colorBytes.asFloatBuffer();
            colorBuffer.put(colors);
            colorBuffer.position(0);

        }
    }
}




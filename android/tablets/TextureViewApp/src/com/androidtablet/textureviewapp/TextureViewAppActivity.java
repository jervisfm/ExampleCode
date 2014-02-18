package com.androidtablet.textureviewapp;

import android.os.Bundle;
import android.app.Activity;
import android.hardware.Camera;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import android.widget.FrameLayout;
import java.io.IOException;
import android.view.Gravity;
import android.widget.Toast;

public class TextureViewAppActivity extends Activity implements TextureView.SurfaceTextureListener  {
    private Camera camera;
    private TextureView textureView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textureView = new TextureView(this);
        textureView.setSurfaceTextureListener(this);
        setContentView(textureView);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        camera = Camera.open();    
        Camera.Size previewSize = camera.getParameters().getPreviewSize();                   
        textureView.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));                  
        try {
            camera.setPreviewTexture(surface);    
        } catch (IOException t) { }
        camera.startPreview();                   
        textureView.setScaleX(1.5f);
        textureView.setRotation(180.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) { }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.stopPreview();
        camera.release();
        return true;
    }

    @Override
   public void onSurfaceTextureUpdated(SurfaceTexture surface){}
}

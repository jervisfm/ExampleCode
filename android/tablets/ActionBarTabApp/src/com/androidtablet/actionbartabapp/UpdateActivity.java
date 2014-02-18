package com.androidtablet.actionbartabapp;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;

public class UpdateActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup 
        container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.updatefragment, 
            container, false);
    }
}

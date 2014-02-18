package com.androidtablet.foregroundfragmentapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

public class Fragment2Activity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    public void dispOption(String msg){
        TextView selectedOpt = (TextView) getActivity().findViewById(R.id.selectedopt);
        selectedOpt.setText("You have selected "+msg);   
    } 
}

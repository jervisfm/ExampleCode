package com.androidtablet.fragmentsbycodeapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

public class Fragment2Activity extends Fragment {
    TextView selectedOpt;
    String messageReceived="";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw= inflater.inflate(R.layout.fragment2,  container, false);
           Bundle bundle=getArguments();  
           if(bundle !=null) {
               messageReceived = bundle.getString("msg");
               selectedOpt = (TextView) vw.findViewById(R.id.selectedopt);
               selectedOpt.setText(messageReceived);
           }
           return vw;      
    }

    public void dispOption(String msg){
        TextView selectedOpt = (TextView) getActivity().findViewById(R.id.selectedopt);
        selectedOpt.setText("You have selected "+msg);   
    } 
}

package com.androidtablet.fragmentsbycodeapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.app.Activity;

public class Fragment1Activity extends Fragment {
    OnOptionSelectedListener  myListener;
    @Override
    public View onCreateView(LayoutInflater inflater, 
        ViewGroup container, Bundle savedInstanceState) {
        Context c = getActivity().getApplicationContext();
        View vw = inflater.inflate(R.layout.fragment1, container, false);
        String[] products={"Camera", "Laptop", "Watch", "Smartphone", "Television"};
        ListView productsList = (ListView) vw.findViewById(R.id.products_list);
        ArrayAdapter<String> arrayAdpt= new ArrayAdapter <String>(c, R.layout.list_item, products);
        productsList.setAdapter(arrayAdpt);
        productsList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                myListener.onOptionSelected(((TextView) v).getText().toString());   
            }
        });
        return vw;
    }
    
    public interface OnOptionSelectedListener {
        public void onOptionSelected(String message);
    }

    @Override
    public void onAttach(Activity activity) { 
        super.onAttach(activity); 
        try { 
            myListener = (OnOptionSelectedListener) activity; 
        } catch (ClassCastException e) { 
        throw new ClassCastException(activity.toString() + " must implement OnItemClickListener"); 
        } 
    }  
}

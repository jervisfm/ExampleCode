package com.androidtablet.foregroundfragmentapp;

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
import android.content.res.Configuration;
import android.content.Intent;


public class Fragment1Activity extends Fragment {
    OnOptionSelectedListener  myListener;
    boolean large, xlarge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context c = getActivity().getApplicationContext();
        View vw = inflater.inflate(R.layout.fragment1, container, false);
        String[] products={"Camera", "Laptop", "Watch",  "Smartphone", "Television"};
        large = ((getResources().getConfiguration().screenLayout  & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);          	
        xlarge =((getResources().getConfiguration().screenLayout  & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);       
        ListView productsList = (ListView) vw.findViewById(R.id.products_list);
        ArrayAdapter<String> arrayAdpt= new ArrayAdapter<String> (c, R.layout.list_item, products);
        productsList.setAdapter(arrayAdpt);         
        productsList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){              	
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || large || xlarge){                                       
                    myListener.onOptionSelected(((TextView)  v).getText().toString());   
                 } else {
                    Intent intent = new Intent(getActivity().getApplicationContext(),  DisplayItemActivity.class);
                    intent.putExtra("item", ((TextView) v).getText().toString());
                    startActivity(intent);
                 }
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
            throw new ClassCastException(activity.toString() + "  must implement OnItemClickListener"); 
        } 
    }    
} 



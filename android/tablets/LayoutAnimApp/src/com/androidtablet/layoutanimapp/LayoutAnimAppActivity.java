package com.androidtablet.layoutanimapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class LayoutAnimAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_anim_app);
        final String[] products={"Camera", "Laptop", "Watch", "Smartphone", "Television"};
        ListView listView = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdpt= new ArrayAdapter<String>  (getBaseContext(),R.layout.list_item,  products);
        listView.setAdapter(arrayAdpt);   
    }
}

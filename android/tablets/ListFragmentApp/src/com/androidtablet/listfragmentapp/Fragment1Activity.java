package com.androidtablet.listfragmentapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment1Activity extends ListFragment {
    String[] products={"Camera", "Laptop", "Watch",  "Smartphone", "Television"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter  <String>(getActivity(),R.layout.list_item, products);
        setListAdapter(arrayAdpt);
    }

    @Override
    public void onListItemClick(ListView l, View v, int  position, long id) {
        Fragment2Activity frag = (Fragment2Activity) getFragmentManager().findFragmentById(R.id.fragment2);
        frag.dispOption(((TextView) v).getText().toString());                          
    }
}

package com.androidtablet.listpopupwindowapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListPopupWindow;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View.OnClickListener;

public class ListPopupWindowAppActivity extends Activity 
    implements OnItemClickListener {
    EditText productName;
    ListPopupWindow listPopupWindow;
    String[] products={"Camera", "Laptop", "Watch","Smartphone", "Television"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_popup_window_app);
        productName = (EditText) findViewById(R.id.product_name); 
        listPopupWindow = new ListPopupWindow(ListPopupWindowAppActivity.this); 
        listPopupWindow.setAdapter(new ArrayAdapter(ListPopupWindowAppActivity.this,  R.layout.list_item, products)); 
        listPopupWindow.setAnchorView(productName); 
        listPopupWindow.setWidth(300); 
        listPopupWindow.setHeight(400); 
        listPopupWindow.setModal(true);   
        listPopupWindow.setOnItemClickListener(ListPopupWindowAppActivity.this); 
        productName.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                listPopupWindow.show();  
            }        
        });       
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
        productName.setText(products[position]);
        listPopupWindow.dismiss();
    }
}

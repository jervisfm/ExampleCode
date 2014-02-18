package com.androidtablet.popupmenuapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.PopupMenu;
import android.view.MenuItem;

public class PopupMenuAppActivity extends Activity {
    EditText productName;
    PopupMenu popupMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_menu_app);
        productName = (EditText) findViewById(R.id.product_name); 
        popupMenu = new PopupMenu(PopupMenuAppActivity.this, productName);
        popupMenu.getMenuInflater().inflate( R.menu.popupmenu, popupMenu.getMenu()); 
        productName.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {   
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        productName.setText(item.toString());
                        return true;
                    }
                });  
                popupMenu.show();
            }
        });  
    }
}

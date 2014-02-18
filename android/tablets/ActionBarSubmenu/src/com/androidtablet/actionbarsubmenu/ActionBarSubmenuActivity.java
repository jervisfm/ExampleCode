package com.androidtablet.actionbarsubmenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ActionBarSubmenuActivity extends Activity {
    private TextView selectedOpt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_submenu);
        selectedOpt=(TextView)findViewById(R.id.selectedopt);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_action_bar_submenu, 
            menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create: 
                selectedOpt.setText("You have selected Create option");
                break;
            case R.id.update: 
                selectedOpt.setText("You have selected Update option");
                break;         
            case R.id.create_invoice: 
                selectedOpt.setText("You have selected Create Invoice option");
                break;
            case R.id.create_customer: 
                selectedOpt.setText("You have selected Create Customer option");
                break;
            case R.id.create_product: 
                selectedOpt.setText("You have selected Create Product option");
                break;        
            case R.id.update_code: 
                selectedOpt.setText("You have selected Update Code option");
                break;
            case R.id.update_name: 
                selectedOpt.setText("You have selected Update Name option");
                break;
            case R.id.update_price: 
                selectedOpt.setText("You have selected Update Price option");
                break;     
        }
        return true;
    } 
}

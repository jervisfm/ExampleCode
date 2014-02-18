package com.androidtablet.preffragmentapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.TextView;

public class PrefFragmentAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_fragment_app);
        startActivity(new Intent(this, PrefFragActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        TextView newsletter=(TextView)findViewById(R.id.newsletter);
        TextView name=(TextView)findViewById(R.id.name);
        TextView ringtone=(TextView)findViewById(R.id.ringtone);
        TextView product=(TextView)findViewById(R.id.product);
        if(Boolean.valueOf(prefs.getBoolean("Newskey", false)))
            newsletter.setText("You have selected Newsletter");
        else
            newsletter.setText("");
        ringtone.setText("The ringtone selected is "+prefs.getString("Audio", "Silent"));
        name.setText("The name entered is "+prefs.getString("Namekey",""));
        String selectedProduct = prefs.getString("products_list", "Camera"); 
        product.setText(selectedProduct);
    }
}

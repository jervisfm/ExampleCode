package com.androidtablet.foregroundfragmentapp;

import android.app.Activity;
import android.os.Bundle;
import com.androidtablet.foregroundfragmentapp.Fragment1Activity.OnOptionSelectedListener;

public class ForegroundFragmentAppActivity extends Activity implements OnOptionSelectedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_fragment_app);
    } 
    public void onOptionSelected(String msg) {
        Fragment2Activity frag2 = (Fragment2Activity) getFragmentManager().findFragmentById(R.id.fragment2);
        frag2.dispOption(msg);   
    }  
}

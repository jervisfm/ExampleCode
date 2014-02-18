package com.androidtablet.fragmentsbycodeapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import com.androidtablet.fragmentsbycodeapp.Fragment1Activity.OnOptionSelectedListener;

public class FragmentsByCodeAppActivity extends Activity implements OnOptionSelectedListener {
    private static final String TAG1 = "1";
    private static final String TAG2 = "2";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_by_code_app);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(null==fragmentManager.findFragmentByTag(TAG1)){
            Fragment1Activity fragment1 = new Fragment1Activity();
            fragmentTransaction.add(R.id.frag1_container, fragment1, TAG1);  
        }
        Bundle args = new Bundle(); 
        String message="Message from Fragment 1";   
        if(null==fragmentManager.findFragmentByTag(TAG2)){
            Fragment2Activity fragment2 = new Fragment2Activity(); 
            args.putString("msg", message); 
            fragment2.setArguments(args); 
            fragmentTransaction.add(R.id.frag2_container, fragment2, TAG2);  
        }
        fragmentTransaction.commit(); 
        }

    public void onOptionSelected(String msg){
        Fragment2Activity frag2 = (Fragment2Activity) getFragmentManager().findFragmentById(R.id.frag2_container);
        frag2.dispOption(msg);              
    }
}

package com.androidtablet.webviewfragapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View; 
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.view.KeyEvent;
import android.app.FragmentManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class WebViewFragAppActivity extends Activity implements OnClickListener {
	EditText url;
	FragmentManager fragmentManager;
	  Bundle args;
	  WebViewFragmentActivity webviewFragment;
	  android.app.FragmentTransaction fragmentTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_frag_app);
        fragmentManager = getFragmentManager(); 
        webviewFragment = new WebViewFragmentActivity(); 
    	fragmentTransaction = fragmentManager.beginTransaction();
    	  args = new Bundle(); 
        url = (EditText)this.findViewById(R.id.url);
        url.setOnEditorActionListener(new OnEditorActionListener() {  
            @Override  
            public boolean onEditorAction(TextView v, int actionId,
                KeyEvent event) {  
                if(event!=null && event.getAction()==KeyEvent.ACTION_DOWN){  
                	args.putString("url", url.getText().toString()); 
                    webviewFragment.setArguments(args); 
                    fragmentTransaction.replace(R.id.fragment, webviewFragment);
                   fragmentTransaction.commit();
                    return true;                              
                }
                return false;                                 
            }
        });
        Button b = (Button)this.findViewById(R.id.go_button);
        b.setOnClickListener(this);        
	}
	
    @Override
    public void onClick(View v) { 
        args.putString("url", url.getText().toString()); 
        webviewFragment.setArguments(args); 
        fragmentTransaction.replace(R.id.fragment, webviewFragment);
        fragmentTransaction.commit();
    }
}
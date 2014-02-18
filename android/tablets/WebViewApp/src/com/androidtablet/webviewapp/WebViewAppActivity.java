package com.androidtablet.webviewapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View; 
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.view.KeyEvent;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class WebViewAppActivity extends Activity implements OnClickListener {
    EditText url;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_app);
        url = (EditText)this.findViewById(R.id. url);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView 
                view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        url.setOnEditorActionListener(new OnEditorActionListener() {  
        	@Override  
        	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
        	if(event!=null && event.getAction()==KeyEvent.ACTION_DOWN){  
        	webView.loadUrl(url.getText().toString());
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
        webView.loadUrl(url.getText().toString());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) { 
            webView.goBack();  
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}

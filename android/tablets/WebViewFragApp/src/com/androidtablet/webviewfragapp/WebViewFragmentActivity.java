package com.androidtablet.webviewfragapp;

import android.webkit.WebViewFragment;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragmentActivity extends WebViewFragment{
	String webURL;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if (null == state)  state = getArguments(); 
        if (null != state){
    	webURL=state.getString("url");
        }
        setRetainInstance(true); 
    }
    
    @Override 
    public void onActivityCreated(Bundle savedInstanceState) { 
        super.onActivityCreated(savedInstanceState); 
        WebView webView = getWebView(); 
        if (webView != null) { 
          	 webView.getSettings().setJavaScriptEnabled(true);
        	 webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(webURL); 
        } 
    } 
}

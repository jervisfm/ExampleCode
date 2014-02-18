package com.cookbook.viewtoaweb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		myWebView.loadUrl("http://www.devcannon.com/androidcookbook/chapter10/webview/");
	}

	public class WebAppInterface {
	    Context context;

	    WebAppInterface(Context c) {
	        context = c;
	    }

	    @JavascriptInterface
	    public void triggerToast(String toast) {
	        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
	    }
	}
}

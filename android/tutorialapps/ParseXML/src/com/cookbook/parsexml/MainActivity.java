package com.cookbook.parsexml;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv_main);
		
		String xmlOut = "";
		XmlPullParserFactory factory = null;
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		factory.setNamespaceAware(true);
		XmlPullParser xpp = null;
		try {
			xpp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		try {
			xpp.setInput(new StringReader("<node>This is some text</node>"));
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		int eventType = 0;
		try {
			eventType = xpp.getEventType();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if(eventType == XmlPullParser.START_DOCUMENT) {
				xmlOut += "Start of XML Document";
			} else if (eventType == XmlPullParser.START_TAG) {
				xmlOut += "\nStart of tag: "+xpp.getName();
			} else if (eventType == XmlPullParser.END_TAG) {
				xmlOut += "\nEnd of tag: "+xpp.getName();
			} else if (eventType == XmlPullParser.TEXT) {
				xmlOut += "\nText: "+xpp.getText();
			}
			try {
				eventType = xpp.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		xmlOut += "\nEnd of XML Document";
		
		tv.setText(xmlOut);
	}

}

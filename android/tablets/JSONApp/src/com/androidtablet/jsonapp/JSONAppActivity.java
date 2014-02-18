package com.androidtablet.jsonapp;

import android.os.Bundle;
import android.app.Activity;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.TextView;

public class JSONAppActivity extends Activity {
	private JSONObject jObject, jsubObject;
    private TextView jsonData;
    String productInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsonapp);
		jsonData = (TextView)findViewById(R.id.jsondata);
		writeJSON();
		readJSON();
	}
	
	public void writeJSON() {
		jObject = new JSONObject();
		jsubObject = new JSONObject();
		  try {
			  jsubObject.put("packedon", "Aug 2013");
			  jsubObject.put("manfacturingdate", "Jul 2013");
			  jsubObject.put("expirydate", "Dec 2015"); 
			  jObject.put("id", "A101");
			  jObject.put("productname", "Smartphone");
			  jObject.put("price", Double.valueOf(19.99));
			  jObject.put("details", jsubObject);
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
	}
	
	private void readJSON() {		
try{
    productInfo="Product ID: "+jObject.getString("id")+"\n"
    		+"Product Name: " +jObject.getString("productname")+ "\n"
    		+"Price: " + jObject.getString("price")+ "\n";
    JSONObject prodDetails=jObject.getJSONObject("details");
    productInfo+="Packed On: "+prodDetails.getString("packedon")+"\n"
    		+"Manfacturing Date: " +prodDetails.getString("manfacturingdate")+ "\n"
    		+"Expiry Date: " + prodDetails.getString("expirydate")+ "\n";
    jsonData.setText(productInfo);
	}
catch (Exception e) {
	e.printStackTrace();
}
	}
}

package com.androidtablet.jsonarrayapp;

import android.os.Bundle;
import android.app.Activity;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.TextView;
import org.json.JSONArray;

public class JSONArrayAppActivity extends Activity {
    private JSONObject jObject1, jObject2, jsubObject;
    private TextView jsonData;
    String productInfo="";
    JSONArray productsArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsonarray_app);
        jsonData = (TextView)findViewById(R.id.jsondata);
        writeJSON();
        readJSON();
	}

    public void writeJSON() {
        jObject1 = new JSONObject();
        jsubObject = new JSONObject();
        String jString = "{\"id\":\"A102\", \"productname\":\"Laptop\", \"price\":49.99,\"details\": {\"packedon\":\"Oct 2013\", \"manufacturingdate\":\"Sep 2013\", \"expirydate\":\"Mar 2017\"}}";
        try {
            jsubObject.put("packedon", "Aug 2013");
            jsubObject.put("manufacturingdate", "Jul 2013");
            jsubObject.put("expirydate", "Dec 2015");
            jObject1.put("id", "A101");
            jObject1.put("productname", "Smartphone");
            jObject1.put("price", Double.valueOf(19.99));
            jObject1.put("details", jsubObject);
        	jObject2 = new JSONObject(jString); 
  
        } catch (JSONException e) {
            e.printStackTrace();
        }
        productsArray = new JSONArray();
        productsArray.put(jObject1);
        productsArray.put(jObject2); 
    }
    
    private void readJSON() { 
        try{
        	for (int i =0 ; i<productsArray.length();i++) {
        		JSONObject jObject = productsArray.getJSONObject(i);
            productInfo+="\nProduct ID: "+jObject.getString("id") +"\n"  +"Product Name: " +jObject.getString("productname")+ "\n" +"Price: " +  String.valueOf(jObject.getDouble("price"))+ "\n";
            JSONObject prodDetails=jObject.getJSONObject("details");
            productInfo+="Packed On: "+prodDetails.getString("packedon")+"\n" +"Manfacturing Date: " + prodDetails.getString("manufacturingdate")+ "\n"+ "Expiry Date: " + prodDetails.getString("expirydate")+ "\n";  
        	}
            jsonData.setText(productInfo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

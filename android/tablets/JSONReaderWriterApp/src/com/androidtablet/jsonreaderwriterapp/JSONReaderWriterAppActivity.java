package com.androidtablet.jsonreaderwriterapp;

import android.os.Bundle;
import android.app.Activity;
import java.io.StringWriter;
import android.widget.TextView;
import android.util.JsonWriter;
import java.io.IOException;
import java.io.StringReader;
import android.util.JsonReader;
import android.util.JsonToken;

public class JSONReaderWriterAppActivity extends Activity {
    private TextView jsonData;
    String id,productname, productData, productInfo;
	double price;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsonreader_writer_app);
		jsonData = (TextView)findViewById(R.id.jsondata);
		writeJSON();
		readJSON();	
	}

	public void writeJSON() {
		StringWriter stringWriter = new StringWriter();
		 JsonWriter jsonWriter = new JsonWriter(stringWriter);
		  try {
		 jsonWriter.beginObject();
		jsonWriter.name("id").value("A101");
		jsonWriter.name("productname").value("Smartphone");
		jsonWriter.name("price").value(19.99);
		 jsonWriter.endObject();
		  } catch (IOException e) {
			    e.printStackTrace();
			  }
		 productData = stringWriter.toString();	
		  }
	
	private void readJSON() {	
	
		JsonReader jsonReader = new JsonReader(new StringReader(productData));
		try{	
		jsonReader.beginObject();
		 while (jsonReader.hasNext()) {
		     String name = jsonReader.nextName();
		     if (jsonReader.peek() == JsonToken.NULL) {
		         jsonReader.skipValue();
		     }
		     if (name.equals("id")) {
		         id = jsonReader.nextString();
		     }
		     else if (name.equals("productname")) {
		         productname = jsonReader.nextString();
		     }
		     else if (name.equals("price")) {
		         price = jsonReader.nextDouble();
		     }
		     else {
		         jsonReader.skipValue();
		     }
		 }
		 jsonReader.endObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

try{
    productInfo="Product ID: "+id+"\n"
    		+"Product Name: " +productname+ "\n"
    		+"Price: " + String.valueOf(price)+ "\n";
    jsonData.setText(productInfo);
	}
catch (Exception e) {
	e.printStackTrace();
}
	}
}

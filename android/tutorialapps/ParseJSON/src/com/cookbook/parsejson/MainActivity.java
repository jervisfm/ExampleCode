package com.cookbook.parsejson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView tv;
	private JSONObject jsonObject;
	private String jsonString = "{\"item\":{\"name\":\"myName\",\"numbers\":[{\"id\":\"1\"},{\"id\":\"2\"}]}}";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv_main);
		
		try {
			jsonObject = new JSONObject(jsonString);
			JSONObject itemObject = jsonObject.getJSONObject("item");
			String jsonName = "name: " +itemObject.getString("name");
			JSONArray numbersArray = itemObject.getJSONArray("numbers");
			String jsonIds = "";

			for(int i = 0;i < numbersArray.length();i++){
				jsonIds += "id: " + numbersArray.getJSONObject(i).getString("id").toString() + "\n";
			}
			
			tv.setText(jsonName+"\n"+jsonIds);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

package com.androidtablet.consumejsonwebserviceapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.os.AsyncTask;
import org.json.JSONObject;
import org.apache.http.StatusLine;
import org.json.JSONArray;

public class ConsumeJSONWebserviceAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_jsonwebservice_app);
        Button submitButton = (Button)this.findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new Button.OnClickListener(){ 
             public void onClick(View v)  {  
                    new ReadJSONFeed().execute("http://bintuharwani.com/SampleJSON.php");                
             } 
        });
    }
      
    private class ReadJSONFeed extends AsyncTask<String, String, String> {
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... urls) {
       HttpClient httpclient = new DefaultHttpClient(); 
       StringBuilder builder = new StringBuilder();
            HttpPost httppost = new HttpPost(urls[0]); 
           try { 
           HttpResponse response = httpclient.execute(httppost); 
        StatusLine statusLine = response.getStatusLine();
       int statusCode = statusLine.getStatusCode();
       if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } 
    } catch (Exception e) {
        e.printStackTrace();
    }      
    return builder.toString();
        }

        protected void onPostExecute(String result) {
        	String state="";
        	String stateInfo="";
            EditText stateName = (EditText) findViewById(R.id.state_name);
            String searchState=stateName.getText().toString();
        	try{
        	 JSONArray countriesArray = new JSONArray(result);    	
        	       for (int i =0 ; i<countriesArray.length();i++) {
                       JSONObject jObject = countriesArray.getJSONObject(i);
                      state = jObject.getString("state"); 
                      if(searchState.equalsIgnoreCase(state))
                      {
                    	  stateInfo+="Capital: "+jObject.getString("capital")+"\n"; 
                    	  stateInfo+="Latitude: "+jObject.getString("latitude")+"\n"; 
                    	  stateInfo+="Longitude: "+jObject.getString("longitude")+"\n"; 
                      }
                   }
        	}
        	 catch (JSONException e) { 
                 e.printStackTrace(); 
                 } 
            TextView resp = (TextView) findViewById(R.id.response);
            if(stateInfo.trim().length() >0 )
        	 resp.setText(stateInfo);	
            else
            	resp.setText("Sorry no match found");
        }
    }
}

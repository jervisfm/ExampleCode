package com.cookbook.tcookbook;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

		static String TWITTER_CONSUMER_KEY = "bpzXAZNjBNyw2oG7coYrRA"; // Replace with your Consumer key
		static String TWITTER_CONSUMER_SECRET = "qxl7LLDCOCWLW6AfXQfSJfykb8I8zrr4MRVPFdgsU"; // Replace with your Consumer secret

		static String PREFERENCE_NAME = "twitter_oauth";
		static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
		static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
		static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

		static final String TWITTER_CALLBACK_URL = "oauth://tcookbook";

		static final String URL_TWITTER_AUTH = "auth_url";
		static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
		static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

		Button btnLoginTwitter;
		Button btnUpdateStatus;
		Button btnLogoutTwitter;
		EditText txtUpdate;
		TextView lblUpdate;
		TextView lblUserName;

		ProgressDialog pDialog;

		private static Twitter twitter;
		private static RequestToken requestToken;
		
		private static SharedPreferences mSharedPreferences;
		
		private ConnectionDetector cd;
		
		AlertDialogManager adm = new AlertDialogManager();

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			// Used for Android 2.3+
			if (Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
			cd = new ConnectionDetector(getApplicationContext());

			if (!cd.isConnectingToInternet()) {
				adm.showAlertDialog(MainActivity.this, "Internet Connection Error",
						"Please connect to working Internet connection", false);
				return;
			}
			
			if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
				adm.showAlertDialog(MainActivity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
				return;
			}

			btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
			btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
			btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
			txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
			lblUpdate = (TextView) findViewById(R.id.lblUpdate);
			lblUserName = (TextView) findViewById(R.id.lblUserName);

			mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);

			btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// Call login twitter function
					loginToTwitter();
				}
			});

			btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String status = txtUpdate.getText().toString();

					if (status.trim().length() > 0) {
						new updateTwitterStatus().execute(status);
					} else {
						Toast.makeText(getApplicationContext(),"Please enter status message", Toast.LENGTH_SHORT).show();
					}
				}
			});

			btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// Call logout twitter function
					logoutFromTwitter();
				}
			});

			if (!isTwitterLoggedInAlready()) {
				Uri uri = getIntent().getData();
				if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
					String verifier = uri.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

					try {
						AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

						Editor e = mSharedPreferences.edit();

						e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
						e.putString(PREF_KEY_OAUTH_SECRET,accessToken.getTokenSecret());
						e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
						e.commit();

//						Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

						btnLoginTwitter.setVisibility(View.GONE);

						lblUpdate.setVisibility(View.VISIBLE);
						txtUpdate.setVisibility(View.VISIBLE);
						btnUpdateStatus.setVisibility(View.VISIBLE);
						btnLogoutTwitter.setVisibility(View.VISIBLE);
						
						long userID = accessToken.getUserId();
						User user = twitter.showUser(userID);
						String username = user.getName();
						
						lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
					} catch (Exception e) {
						Log.e("***Twitter Login Error: ",e.getMessage());
					}
				}
			}

		}

		private void loginToTwitter() {
			if (!isTwitterLoggedInAlready()) {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
				Configuration configuration = builder.build();
				
				TwitterFactory factory = new TwitterFactory(configuration);
				twitter = factory.getInstance();

				if(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
					try {
						requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
						this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
					} catch (TwitterException e) {
						e.printStackTrace();
					}
					} else {
					new Thread(new Runnable() {
						public void run() {
							try {	
								requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
								MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
							} catch (TwitterException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			} else {
				Toast.makeText(getApplicationContext(),"Already Logged into twitter", Toast.LENGTH_LONG).show();
			}
		}

		class updateTwitterStatus extends AsyncTask<String, String, String> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(MainActivity.this);
				pDialog.setMessage("Updating to twitter...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			protected String doInBackground(String... args) {
//				Log.d("*** Text Value of Tweet: ",args[0]);
				String status = args[0];
				try {
					ConfigurationBuilder builder = new ConfigurationBuilder();
					builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
					builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
					
					String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
					String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
					
					AccessToken accessToken = new AccessToken(access_token, access_token_secret);
					Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
					
					twitter4j.Status response = twitter.updateStatus(status);
					
//					Log.d("*** Update Status: ",response.getText());
				} catch (TwitterException e) {
					Log.d("*** Twitter Update Error: ", e.getMessage());
				}
				return null;
			}

			protected void onPostExecute(String file_url) {
				pDialog.dismiss();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(),"Status tweeted successfully", Toast.LENGTH_SHORT).show();
						txtUpdate.setText("");
					}
				});
			}

		}

		private void logoutFromTwitter() {
			Editor e = mSharedPreferences.edit();
			e.remove(PREF_KEY_OAUTH_TOKEN);
			e.remove(PREF_KEY_OAUTH_SECRET);
			e.remove(PREF_KEY_TWITTER_LOGIN);
			e.commit();

			btnLogoutTwitter.setVisibility(View.GONE);
			btnUpdateStatus.setVisibility(View.GONE);
			txtUpdate.setVisibility(View.GONE);
			lblUpdate.setVisibility(View.GONE);
			lblUserName.setText("");
			lblUserName.setVisibility(View.GONE);

			btnLoginTwitter.setVisibility(View.VISIBLE);
		}
		
		private boolean isTwitterLoggedInAlready() {
			return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
		}

		protected void onResume() {
			super.onResume();
		}
	
}

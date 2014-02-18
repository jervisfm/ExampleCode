package com.cookbook.loaderdemo;

import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	private static final int LOADER_ID = 1;
	SimpleCursorAdapter mAdapter;
	ListView mListView;
	
    static final String[] CONTACTS_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView=(ListView)findViewById(R.id.list);
		
		mAdapter=new SimpleCursorAdapter(
				getApplicationContext(), //context for layout inflation etc
				android.R.layout.simple_list_item_1, //the layout file
				null, //we don't have a cursor yet
				new String[]{Contacts.DISPLAY_NAME}, //the name of the data row
				new int[]{android.R.id.text1} //the id of the layout where the data is displayed
		
		);
		
		mListView.setAdapter(mAdapter);
		
		getSupportLoaderManager().initLoader(LOADER_ID,null,this);
	}


	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

		return new CursorLoader(
				getApplicationContext(), 
				Contacts.CONTENT_URI, 
				CONTACTS_PROJECTION, 
				null, 
				null, 
				Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
		);
			
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if(loader.getId()==LOADER_ID){
			mAdapter.swapCursor(cursor);
		}
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if(loader.getId()==LOADER_ID){
			mAdapter.swapCursor(null);
		}
		
		
	}

}

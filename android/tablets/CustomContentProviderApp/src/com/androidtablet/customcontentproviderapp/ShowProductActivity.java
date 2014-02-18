package com.androidtablet.customcontentproviderapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class ShowProductActivity extends ListActivity implements LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showproduct);  
        String[] columns = new String[] { ProductsProvider.PRODUCT};  
        int[] toIds = new int[] {R.id.product_name};  
        getLoaderManager().initLoader(0, null,this);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, null, columns, toIds, 0);
        setListAdapter(adapter);
    }    
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {ProductsProvider.ID, ProductsProvider.PRODUCT, ProductsProvider.PRICE} ;  
        CursorLoader cursorLoader = new CursorLoader(this, ProductsProvider.CONTENT_URI, projection, null,  null, null);  
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor  data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, MaintainProductActivity.class);
        Uri uri = Uri.parse(ProductsProvider.CONTENT_URI + "/" + id);
        intent.putExtra(ProductsProvider.CONTENT_ITEM_TYPE, uri);
        startActivity(intent);
    }
}

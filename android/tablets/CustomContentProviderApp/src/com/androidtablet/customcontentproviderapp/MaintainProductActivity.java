package com.androidtablet.customcontentproviderapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.net.Uri;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.Toast;

public class MaintainProductActivity extends Activity {
    EditText productName, price;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintainproduct);  
        productName  = (EditText) findViewById(R.id.product_name);
        price  = (EditText) findViewById(R.id.price);
        Bundle extras = getIntent().getExtras();
        uri = (extras == null) ? null: (Uri) extras.getParcelable(ProductsProvider.CONTENT_ITEM_TYPE);
        if (extras != null) {
            uri = extras.getParcelable(ProductsProvider.CONTENT_ITEM_TYPE);
            String[] projection = new String[] {ProductsProvider.ID, ProductsProvider.PRODUCT, ProductsProvider.PRICE} ;  
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                productName.setText(cursor.getString(cursor.getColumnIndexOrThrow (ProductsProvider.PRODUCT)));
                price.setText(cursor.getString(cursor.getColumnIndexOrThrow (ProductsProvider.PRICE)));
                cursor.close();
            }
        }
        Button deleteProductInfo  = (Button) findViewById(R.id.delete_productinfo);
        Button updateProductInfo = (Button) findViewById(R.id.update_productinfo);
        deleteProductInfo.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                int count = getContentResolver().delete(uri, null, null);
                if(count >0)
                    Toast.makeText(MaintainProductActivity.this, "Row deleted", Toast.LENGTH_SHORT).show(); 
            } 
        });    
        updateProductInfo.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues(); 
                contentValues.put(ProductsProvider.PRODUCT, productName .getText().toString());
                contentValues.put(ProductsProvider.PRICE, price.getText().toString());
                getContentResolver().update(uri, contentValues, null,null);
                Toast.makeText(MaintainProductActivity.this, "Row updated", Toast.LENGTH_SHORT).show(); 
            } 
        });    
    } 
}

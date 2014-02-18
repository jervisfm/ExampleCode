package com.androidtablet.customcontentproviderapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.ContentValues;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

public class CustomContentProviderAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_content_provider_app);
        Button addProductButton = (Button) this.findViewById(R.id.add_productinfo);
        addProductButton.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues(); 
                EditText productName = (EditText) findViewById(R.id.product_name);
                EditText productPrice = (EditText) findViewById(R.id.price);
                contentValues.put(ProductsProvider.PRODUCT, productName.getText().toString());
                contentValues.put(ProductsProvider.PRICE, productPrice.getText().toString());
                getContentResolver().insert(ProductsProvider.CONTENT_URI, contentValues);
                Toast.makeText(CustomContentProviderAppActivity.this, "Row inserted", Toast.LENGTH_SHORT).show();
                productName.setText("");
                productPrice.setText("");
            } 
        });           

        Button listProductButton = (Button)this.findViewById(R.id.list_productinfo);
        listProductButton.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v) {
               startActivity(new Intent(CustomContentProviderAppActivity.this, ShowProductActivity.class));
            } 
        });     
    }
}

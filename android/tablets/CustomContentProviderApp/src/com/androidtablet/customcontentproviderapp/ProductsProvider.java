package com.androidtablet.customcontentproviderapp;

import android.content.ContentProvider;
import android.content.UriMatcher;
import android.net.Uri;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.content.ContentUris;
import android.database.SQLException;
import android.database.Cursor;
import android.text.TextUtils;
import android.database.sqlite.SQLiteQueryBuilder;
import android.content.ContentResolver;

public class ProductsProvider extends ContentProvider {
    static final String DB_NAME = "Products.db";
    static final String DB_TABLE = "productinfo";
    static final int DB_VERSION = 1;
    static final String CREATE_TABLE ="CREATE TABLE " + DB_TABLE  + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, product TEXT not null, price TEXT not null);"; 
    static final String ID = "_id";
    static final String PRODUCT = "product";
    static final String PRICE = "price";
    static final String AUTHORITY="com.bmharwani.provider.Products";
    static final Uri CONTENT_URI =Uri.parse("content://"+ AUTHORITY+"/productinfo");
    static final int ALLROWS = 1;
    static final int SINGLEROW = 2;
    private static final UriMatcher URIMATCHER;
    static{
        URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URIMATCHER.addURI(AUTHORITY, "productinfo", ALLROWS);
        URIMATCHER.addURI(AUTHORITY, "productinfo/#", SINGLEROW);
    }
    SQLiteDatabase ProductsDB;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/productinfo";

    @Override
    public boolean onCreate() {
        Context context = getContext();
        SQHelper helper = new SQHelper(context);
        ProductsDB = helper.getWritableDatabase();
        return (ProductsDB == null)? false:true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URIMATCHER.match(uri)){
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.products.productinfo";
            case SINGLEROW:
                return "vnd.android.cursor.item/vnd.products.productinfo";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    } 

    @Override
    public Cursor query(Uri uri, String[] projection, String criteria, String[] criteriaValues, String sortColumn) {        
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DB_TABLE);
        if (URIMATCHER.match(uri) == SINGLEROW) queryBuilder.appendWhere(ID + " = " + uri.getPathSegments().get(1));
        if (sortColumn==null || sortColumn=="") sortColumn = "product";
        Cursor c = queryBuilder.query(ProductsDB,projection, criteria,criteriaValues,null,null,sortColumn);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c; 
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
       long rowID = ProductsDB.insert(DB_TABLE,null, contentValues);
       if (rowID >0) {
           Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
           getContext().getContentResolver().notifyChange(_uri, null);
           return _uri;
       }
       throw new SQLException("Error: New row could not be inserted ");
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String criteria, String[] criteriaValues) {
        int count = 0;
        switch (URIMATCHER.match(uri)){
            case ALLROWS:
                count = ProductsDB.update(DB_TABLE, contentValues, criteria,criteriaValues);
                break;
            case SINGLEROW:
                count = ProductsDB.update(DB_TABLE,  contentValues, ID + " = " + uri.getPathSegments().get(1) +(! TextUtils.isEmpty(criteria) ? " AND (" +criteria + ')': ""),criteriaValues);
                break;
            default: throw new IllegalArgumentException("URI not found: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri rowUri, String criteria, String[] criteriaValues) {
        int count=0;
        switch (URIMATCHER.match(rowUri)){
            case ALLROWS:
                count = ProductsDB.delete(DB_TABLE, criteria, criteriaValues);
                break;
            case SINGLEROW: 
                String id = rowUri.getPathSegments().get(1);
                count = ProductsDB.delete(DB_TABLE, ID + " = " + id +(!TextUtils.isEmpty(criteria) ? " AND ("+criteria + ')': ""),criteriaValues);
                break;
            default: throw new IllegalArgumentException("URI not found: " + rowUri);
        }
        getContext().getContentResolver().notifyChange(rowUri,  null);
        return count;
    } 

    private static class SQHelper extends SQLiteOpenHelper {
        SQHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
            onCreate(db);
        }
    }    
}

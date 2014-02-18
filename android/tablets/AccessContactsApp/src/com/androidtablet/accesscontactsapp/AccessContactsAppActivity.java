package com.androidtablet.accesscontactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.net.Uri;
import android.database.Cursor;
import android.content.CursorLoader;
import android.provider.ContactsContract;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class AccessContactsAppActivity extends Activity {
    ArrayList<String> contactRows=new ArrayList<String>();
    final String[] nocontact={"No Contacts on the Device"};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_contacts_app);
        final ListView contactsList=(ListView) findViewById(R.id.contactslist);
        Uri contactsUri = Uri.parse("content://contacts/people");
        String[] projection = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
        Cursor c;            
        CursorLoader cursorLoader = new CursorLoader(this,contactsUri, projection, null, null , null);
        c = cursorLoader.loadInBackground();
        contactRows.clear();
        c.moveToFirst();
        while(c.isAfterLast()==false){
            String contactID = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            String contactDisplayName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contactRows.add(contactID+ " "+contactDisplayName);
            c.moveToNext();
        }
        if (c != null && !c.isClosed()) {  
            c.close();  
        } 
        if(contactRows.isEmpty()) {
            ArrayAdapter<String> arrayAdpt=new  ArrayAdapter <String>(this, R.layout.list_item, nocontact); 
            contactsList.setAdapter(arrayAdpt);  
        }
        else {
            ArrayAdapter<String> arrayAdpt=new ArrayAdapter <String>(this, R.layout.list_item, contactRows); 
            contactsList.setAdapter(arrayAdpt);  
        }
    }
}

package com.androidtablet.copypasteapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipboardManager;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.ClipData;

public class CopyPasteAppActivity extends Activity {
    EditText editText1, editText2;
    ClipboardManager clipManager;
    Button cutButton, copyButton, pasteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_paste_app);
        editText1 = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);
        clipManager= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cutButton = (Button)this.findViewById(R.id.cut_button);
        cutButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                ClipData clipData = ClipData.newPlainText("data", editText1.getText());
                clipManager.setPrimaryClip(clipData);
                editText1.setText("");
            }
        });
        copyButton = (Button)this.findViewById(R.id.copy_button);
        copyButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                ClipData clipData = ClipData.newPlainText("data", editText1.getText());
                clipManager.setPrimaryClip(clipData);
            }
        });
        pasteButton = (Button)this.findViewById( R.id.paste_button);
        pasteButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                if (clipManager.hasPrimaryClip()){
                    ClipData clipData = clipManager.getPrimaryClip();
                  editText2.setText(clipData.getItemAt(0).getText());   
               }
            }
        });  
    }
}

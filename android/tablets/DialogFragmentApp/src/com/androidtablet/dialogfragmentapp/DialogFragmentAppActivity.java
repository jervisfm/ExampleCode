package com.androidtablet.dialogfragmentapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class DialogFragmentAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment_app);
        Button dialogButton = (Button)findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Fragment1Activity dialogFragment = 
                    Fragment1Activity.newInstance( "Continue Processing?");
                dialogFragment.show(getFragmentManager(),  "Dialog Fragment Example");
            }
        });
    }

    public void PositiveButton() {
        TextView selectedOpt = (TextView)findViewById(R.id.selectedopt);
        selectedOpt.setText("You have selected OK button");
    }

    public void NegativeButton() {
        TextView selectedOpt = (TextView) findViewById(R.id.selectedopt);
        selectedOpt.setText("You have selected Cancel button");    
    }
}

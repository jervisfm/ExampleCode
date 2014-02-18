package com.androidtablet.dialogfragmentapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Fragment1Activity extends DialogFragment{
    static Fragment1Activity newInstance(String title) {
        Fragment1Activity fragment = new Fragment1Activity();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {	
        String title = getArguments().getString("title");
        Dialog diag = new AlertDialog.Builder(getActivity())
        .setIcon(R.drawable.ic_launcher)
        .setTitle(title)
        .setPositiveButton("OK", new DialogInterface. 
            OnClickListener() {
            public void onClick(DialogInterface dialog, int 
                whichButton) {
                ((DialogFragmentAppActivity) getActivity()).PositiveButton();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface. 
            OnClickListener() {
            public void onClick(DialogInterface dialog, int 
                whichButton) {
                ((DialogFragmentAppActivity) getActivity()).NegativeButton();
            }
        }).create();
  
        return diag;
    }
}

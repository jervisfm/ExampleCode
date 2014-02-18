package com.cookbook.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

// When using the support lib, use FragmentActivity
public class MainActivity extends FragmentActivity implements ItemFragment.OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//If using large layout use the Support Fragment Manager
		if (findViewById(R.id.fragment_container) != null) {
			if(savedInstanceState != null){
				return;
			}
			
			ItemFragment firstFragment = new ItemFragment();
			firstFragment.setArguments(getIntent().getExtras());
			
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
		}
		
	}

	public void onItemSelected(int position) {
        TextFragment textFrag = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.text_fragment);

        if (textFrag != null) {
            textFrag.updateTextView(position);
        } else {
            TextFragment newFragment = new TextFragment();
            Bundle args = new Bundle();
            args.putInt(TextFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}

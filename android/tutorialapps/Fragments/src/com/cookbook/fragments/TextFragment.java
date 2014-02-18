package com.cookbook.fragments;

import com.cookbook.fragments.Strings;
import com.cookbook.fragments.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment {
	final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.text_view, container, false);
	}
	
	@Override
    public void onStart() {
		super.onStart();
		
		Bundle args = getArguments();
        if (args != null) {
            updateTextView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateTextView(mCurrentPosition);
        } else {
        	TextView tv = (TextView) getActivity().findViewById(R.id.text);
        	tv.setText("Select an item from the list.");
        }
		
	}
	
	public void updateTextView(int position){
		TextView tv = (TextView) getActivity().findViewById(R.id.text);
		tv.setText(Strings.Text[position]);
		mCurrentPosition = position;
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
	
}

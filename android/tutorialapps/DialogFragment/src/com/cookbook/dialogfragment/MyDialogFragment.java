package com.cookbook.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
	static MyDialogFragment newInstance() {
		MyDialogFragment mdf = new MyDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", "Dialog Fragment");
		mdf.setArguments(args);
		return mdf;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		Dialog myDialog = new AlertDialog.Builder(getActivity())
				.setIcon(R.drawable.ic_launcher)
				.setTitle(title)
				.setPositiveButton("Protest", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						((MainActivity) getActivity()).protestClicked();
					}
				})
				.setNegativeButton("Forget", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						((MainActivity) getActivity()).forgetClicked();
					}
				}).create();

		return myDialog;
	}
}

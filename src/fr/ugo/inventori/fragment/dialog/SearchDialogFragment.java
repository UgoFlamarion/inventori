package fr.ugo.inventori.fragment.dialog;

import fr.ugo.inventori.R;
import fr.ugo.inventori.R.id;
import fr.ugo.inventori.R.layout;
import fr.ugo.inventori.R.string;
import fr.ugo.inventori.activity.ItemListActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchDialogFragment extends AbstractDialogFragment {

	private Toast invalidFilterToast;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		invalidFilterToast = Toast.makeText(getActivity(), R.string.invalidFilter, Toast.LENGTH_LONG);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.search_popup_title);
		View destockPopupView = inflater.inflate(R.layout.search_popup, null);

		builder.setView(destockPopupView).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				EditText searchFilterEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.search_filter);
				String searchPattern = searchFilterEditText.getText().toString();

				if (searchPattern == null || searchPattern.isEmpty()) {
					invalidFilterToast.show();
					return;
				}
				
				((ItemListActivity) getActivity()).setSearchPattern(searchPattern);
				((ItemListActivity) getActivity()).getActionBar().setDisplayHomeAsUpEnabled(true);
				((ItemListActivity) getActivity()).restartActivity();
			}
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				SearchDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

}

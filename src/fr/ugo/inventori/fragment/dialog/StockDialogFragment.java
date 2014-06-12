package fr.ugo.inventori.fragment.dialog;

import fr.ugo.inventori.R;
import fr.ugo.inventori.R.layout;
import fr.ugo.inventori.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class StockDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.destock_popup_title);
		builder.setView(inflater.inflate(R.layout.destock_popup, null)).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// sign in the user ...
			}
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				StockDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

}

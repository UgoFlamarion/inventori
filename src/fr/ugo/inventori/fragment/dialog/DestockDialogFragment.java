package fr.ugo.inventori.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.ugo.inventori.R;
import fr.ugo.inventori.R.id;
import fr.ugo.inventori.R.layout;
import fr.ugo.inventori.R.string;
import fr.ugo.inventori.db.model.Item;

public class DestockDialogFragment extends AbstractDialogFragment {

	private Item item;
	private Toast quantityTooHighToast;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		quantityTooHighToast = Toast.makeText(getActivity(), R.string.quantityTooHigh, Toast.LENGTH_LONG);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.destock_popup_title);
		View destockPopupView = inflater.inflate(R.layout.destock_popup, null);

		((EditText) destockPopupView.findViewById(R.id.destock_quantity)).addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s == null || "".equals(s.toString()))
					return;

				Long destockQuantity = Long.valueOf(s.toString());
				Long quantity = item.getQuantity();

				if (destockQuantity > quantity) {
					s.clear();
					s.append(String.valueOf(quantity));
					quantityTooHighToast.show();
				}

			}
		});

		builder.setView(destockPopupView).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				EditText quantityEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.destock_quantity);
				Long destockQuantity = Long.valueOf(quantityEditText.getText().toString());
				Long finalQuantity = item.getQuantity() - destockQuantity;

				item.setQuantity(finalQuantity);
				itemsRepository.update(item);
			}
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				DestockDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

	public void setItem(Item item) {
		this.item = item;
	}

}

package fr.ugo.inventori.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import fr.ugo.inventori.R;
import fr.ugo.inventori.activity.ItemDetailActivity;
import fr.ugo.inventori.activity.ItemListActivity;
import fr.ugo.inventori.db.model.Item;

/**
 * A fragment representing a single Item detail screen. This fragment is either contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class NewItemDialogFragment extends AbstractDialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// invalidFilterToast = Toast.makeText(getActivity(), R.string.invalidFilter, Toast.LENGTH_LONG);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.new_item_title);
		View destockPopupView = inflater.inflate(R.layout.new_item_popup, null);

		builder.setView(destockPopupView).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				addNewItem((AlertDialog) dialog);
				((ItemListActivity) getActivity()).restartActivity();
			}
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				NewItemDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

	public void addNewItem(AlertDialog dialog) {
		String label = ((EditText) dialog.findViewById(R.id.item_label)).getText().toString();
		String quantity = ((EditText) dialog.findViewById(R.id.item_new_quantity)).getText().toString();

		Item item = new Item(label, Long.valueOf(quantity));
		itemsRepository.create(item);
	}
}

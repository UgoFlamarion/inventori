package fr.ugo.inventori.fragment.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import fr.ugo.inventori.R;
import fr.ugo.inventori.activity.ItemListActivity;
import fr.ugo.inventori.db.model.Item;
import fr.ugo.inventori.db.model.Order;
import fr.ugo.inventori.db.model.OrderStatusEnum;

public class OrderDialogFragment extends AbstractDialogFragment {

	private Item item;

	// private Toast quantityTooHighToast;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// quantityTooHighToast = Toast.makeText(getActivity(), R.string.quantityTooHigh, Toast.LENGTH_LONG);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.order_popup_title);
		View orderPopupView = inflater.inflate(R.layout.order_popup, null);

		// ((EditText) destockPopupView.findViewById(R.id.destock_quantity)).addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int count) {
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// if (s == null || "".equals(s.toString()))
		// return;
		//
		// Long destockQuantity = Long.valueOf(s.toString());
		// Long quantity = item.getQuantity();
		//
		// if (destockQuantity > quantity) {
		// s.clear();
		// s.append(String.valueOf(quantity));
		// quantityTooHighToast.show();
		// }
		//
		// }
		// });

		builder.setView(orderPopupView).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				EditText quantityEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.order_quantity);
				Long orderQuantity = Long.valueOf(quantityEditText.getText().toString());
				// Long finalQuantity = item.getQuantity() - destockQuantity;
				List<Order> existingOrders = ordersRepository.findByItemId(item.getId(), OrderStatusEnum.TO_REQUEST);

				if (existingOrders != null && !existingOrders.isEmpty()) {
					// We should have only one order
					// Testing this condition would not make sense though
					Order order = existingOrders.get(0);
					
					order.setQuantity(order.getQuantity() + orderQuantity);
					ordersRepository.update(order);
				} else
					ordersRepository.create(new Order(item, orderQuantity));
				((ItemListActivity) getActivity()).restartActivity();
			}
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				OrderDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

	public void setItem(Item item) {
		this.item = item;
	}

}

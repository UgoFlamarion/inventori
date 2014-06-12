package fr.ugo.inventori.fragment.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import fr.ugo.inventori.db.repository.ItemsRepository;
import fr.ugo.inventori.db.repository.OrdersRepository;

public abstract class AbstractDialogFragment extends DialogFragment {

	protected ItemsRepository itemsRepository;
	
	protected OrdersRepository ordersRepository;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itemsRepository = new ItemsRepository(getActivity());
		ordersRepository = new OrdersRepository(getActivity());
	}
	
}

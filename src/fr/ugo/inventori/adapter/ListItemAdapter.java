package fr.ugo.inventori.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ugo.inventori.R;
import fr.ugo.inventori.activity.AbstractFragmentActivity;
import fr.ugo.inventori.db.model.Item;
import fr.ugo.inventori.db.model.Order;
import fr.ugo.inventori.db.model.OrderStatusEnum;
import fr.ugo.inventori.db.repository.OrdersRepository;
import fr.ugo.inventori.fragment.dialog.DestockDialogFragment;
import fr.ugo.inventori.fragment.dialog.OrderDialogFragment;
import fr.ugo.inventori.fragment.dialog.StockDialogFragment;

public class ListItemAdapter extends BaseAdapter {

	private Context context;
	private Object[] items;
	private static LayoutInflater inflater = null;
	
	private OrdersRepository ordersRepository;

	public ListItemAdapter(Context context, Object[] objects) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.items = objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.ordersRepository = new OrdersRepository(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, final View convertView, ViewGroup parent) {
		final View view = (convertView == null ? inflater.inflate(R.layout.fragment_list_item, null) : convertView);

//		CheckBox checkBox = (CheckBox) view.findViewById(R.id.list_item_checkbox);
		TextView itemLabel = (TextView) view.findViewById(R.id.list_item_label);
		TextView itemQuantity = (TextView) view.findViewById(R.id.list_item_quantity);
//		LinearLayout buttonSet = (LinearLayout) view.findViewById(R.id.list_item_button_set);

		final Item item = (Item) items[position];

		itemLabel.setText(item.getLabel());
		itemQuantity.setText(String.valueOf(item.getQuantity()));

//		if (position == 0) {
//			buttonSet.setVisibility(View.GONE);
//			checkBox.setVisibility(View.GONE);
//		}
		
		List<Order> ordersToRequest = ordersRepository.findByItemId(item.getId(), OrderStatusEnum.TO_REQUEST);
		if (ordersToRequest == null || ordersToRequest.isEmpty())
			((ImageView) view.findViewById(R.id.ic_to_order)).setVisibility(View.GONE);

		List<Order> ordersToBeDelivered = ordersRepository.findByItemId(item.getId(), OrderStatusEnum.WAITING_DELIVERY);
		if (ordersToBeDelivered == null || ordersToBeDelivered.isEmpty())
			((ImageView) view.findViewById(R.id.ic_delivery)).setVisibility(View.GONE);
		
		((ImageButton) view.findViewById(R.id.list_item_destock)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DestockDialogFragment destockDialogFragment = new DestockDialogFragment();
				destockDialogFragment.setItem(item);

				destockDialogFragment.show(((AbstractFragmentActivity) context).getSupportFragmentManager(), "DestockDialogFragment");
			}
		});

		((ImageButton) view.findViewById(R.id.list_item_order)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OrderDialogFragment orderDialogFragment = new OrderDialogFragment();
				orderDialogFragment.setItem(item);

				orderDialogFragment.show(((AbstractFragmentActivity) context).getSupportFragmentManager(), "DestockDialogFragment");
			}
		});

		((CheckBox) view.findViewById(R.id.list_item_checkbox)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				item.setChecked(isChecked);
				
				Menu menu = ((AbstractFragmentActivity) context).getMenu();
				MenuItem actionBarDeleteButton = menu.findItem(R.id.action_delete);
				
				for (Object o : items) {
					Item itemToTest = (Item) o;
					if (itemToTest.isChecked()) {
						actionBarDeleteButton.setEnabled(true);
						actionBarDeleteButton.getIcon().setAlpha(255);
						return ;
					}
				}
				actionBarDeleteButton.setEnabled(false);
				actionBarDeleteButton.getIcon().setAlpha(64);
			}
		});

		return view;
	}

	public void showStockDialog(View view) {
		StockDialogFragment stockDialogFragment = new StockDialogFragment();
		stockDialogFragment.show(((AbstractFragmentActivity) context).getSupportFragmentManager(), "StockDialogFragment");
	}

	public void setItems(Object[] items) {
		this.items= items; 
	}

}

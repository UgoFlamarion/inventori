package fr.ugo.inventori.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import fr.ugo.inventori.R;
import fr.ugo.inventori.adapter.ListItemAdapter;
import fr.ugo.inventori.db.model.Item;
import fr.ugo.inventori.fragment.ItemDetailFragment;
import fr.ugo.inventori.fragment.ItemListFragment;
import fr.ugo.inventori.fragment.dialog.NewItemDialogFragment;
import fr.ugo.inventori.fragment.dialog.SearchDialogFragment;

/**
 * An activity representing a list of Items. This activity has different presentations for handset and tablet-size devices. On handsets, the activity presents a list of items,
 * which when touched, lead to a {@link ItemDetailActivity} representing item details. On tablets, the activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a {@link ItemListFragment} and the item details (if present) is a {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks} interface to listen for item selections.
 */
public class OrderListActivity extends AbstractFragmentActivity implements ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
	 */
	private boolean mTwoPane;

	private List<Item> items;

	private ListItemAdapter listItemAdapter;

	private String searchPattern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);

		initActivity();
	}

	public void initActivity() {
		ItemListFragment itemListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list);

//		if (searchPattern != null && !searchPattern.isEmpty()) {
//			items = itemsRepository.labelLike(searchPattern);
//			getActionBar().setTitle(getResources().getString(R.string.search_ab_title) + searchPattern);
//		} else {
//			items = itemsRepository.getAll();
//			getActionBar().setTitle("");
//		}

		// items.add(0, new Item("Create new item ..."));

		listItemAdapter = new ListItemAdapter(this, items.toArray());

		itemListFragment.setListAdapter(listItemAdapter);
		itemListFragment.setEmptyText(getResources().getText(R.string.empty_list));

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			itemListFragment.setActivateOnItemClick(true);

		}

		// if (getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID) != null) {
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// }

		// TODO: If exposing deep links into your app, handle intents here.

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// getActionBar().setTitle("");

		this.menu = menu;
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);

		MenuItem item = menu.findItem(R.id.action_delete);
		item.getIcon().setAlpha(item.isEnabled() ? 255 : 64);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			searchPattern = null;
			getActionBar().setDisplayHomeAsUpEnabled(false);
			restartActivity();
			return true;
		case R.id.action_order_list:
			Intent myIntent = new Intent(OrderListActivity.this, OrderListActivity.class);
			startActivity(myIntent);
			return true;
		case R.id.action_add_item:
			NewItemDialogFragment itemNewFragment = new NewItemDialogFragment();
			itemNewFragment.show(getSupportFragmentManager(), "ItemNewFragment");
			return true;
		case R.id.action_delete:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			Resources res = getResources();
			final List<Item> selectedItems = getCheckedItems();
			String deleteConfirmMsg = String.format(res.getString(R.string.delete_confirm_msg), selectedItems.size());

			builder.setTitle(deleteConfirmMsg).setPositiveButton(R.string.button_ok, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					for (Item itemToDelete : selectedItems) {
						itemsRepository.delete(itemToDelete);
					}
					findViewById(R.id.list_item).refreshDrawableState();
					refreshList();
				}

			}).setNegativeButton(R.string.button_cancel, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			builder.create().show();
			return true;
		case R.id.action_search:
			SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
			searchDialogFragment.show(getSupportFragmentManager(), "SearchDialogFragment");
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}

	private List<Item> getCheckedItems() {
		List<Item> checkedItems = new ArrayList<Item>();
		for (Item item : items)
			if (item.isChecked())
				checkedItems.add(item);
		return checkedItems;
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {

		// if (mTwoPane) {
		// // In two-pane mode, show the detail view in this activity by
		// // adding or replacing the detail fragment using a
		// // fragment transaction.
		// Bundle arguments = new Bundle();
		// arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
		// ItemDetailFragment fragment = new ItemDetailFragment();
		// fragment.setArguments(arguments);
		// getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
		//
		// } else {
		// // In single-pane mode, simply start the detail activity
		// // for the selected item ID.
		// Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		// detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
		// startActivity(detailIntent);
		// }
	}

	private void refreshList() {
		items = itemsRepository.getAll();
		items.add(0, new Item("Create new item ..."));

		listItemAdapter.setItems(items.toArray());
		listItemAdapter.notifyDataSetChanged();
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	@Override
	public void onBackPressed() {
		if (searchPattern != null && !searchPattern.isEmpty()) {
			searchPattern = null;
			restartActivity();
		} else
			super.onBackPressed();
	}

}

package fr.ugo.inventori.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import fr.ugo.inventori.db.repository.ItemsRepository;

public abstract class AbstractFragmentActivity extends FragmentActivity {

	protected ItemsRepository itemsRepository;

	protected Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itemsRepository = new ItemsRepository(this);
	}

	public Menu getMenu() {
		return menu;
	}

	public void restartActivity() {
//		Intent intent = getIntent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		startActivity(intent);
		initActivity();
	}

	public abstract void initActivity();
	
}

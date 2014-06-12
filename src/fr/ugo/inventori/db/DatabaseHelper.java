package fr.ugo.inventori.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import fr.ugo.inventori.db.model.Item;
import fr.ugo.inventori.db.model.Order;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "ormLiteTutorial.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<Item, Integer> itemsDao = null;
	private Dao<Order, Integer> ordersDao = null;
	
	private RuntimeExceptionDao<Item, Integer> itemsRuntimeDao = null;
	private RuntimeExceptionDao<Order, Integer> ordersRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		for (String databaseName : context.databaseList()) context.deleteDatabase(databaseName);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Item.class);
			TableUtils.createTable(connectionSource, Order.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
//		RuntimeExceptionDao<Item, Integer> dao = getItemsDataDao();
		// create some entries in the onCreate
//		Item item = new Item("First Test Item");
//		dao.create(item);
		Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate");
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Item.class, true);
			TableUtils.dropTable(connectionSource, Order.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for ItemsDao class. It
	 * will create it or just give the cached value.
	 */
	public Dao<Item, Integer> getItemsDao() throws SQLException {
		if (itemsDao == null) {
			itemsDao = getDao(Item.class);
		}
		return itemsDao;
	}

	/**
	 * Returns the Database Access Object (DAO) for ItemsDao class. It
	 * will create it or just give the cached value.
	 */
	public Dao<Order, Integer> getOrdersDao() throws SQLException {
		if (ordersDao == null) {
			ordersDao = getDao(Order.class);
		}
		return ordersDao;
	}
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our SimpleData class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Item, Integer> getItemsDataDao() {
		if (itemsRuntimeDao == null) {
			itemsRuntimeDao = getRuntimeExceptionDao(Item.class);
		}
		return itemsRuntimeDao;
	}
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our SimpleData class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Order, Integer> getOrdersDataDao() {
		if (ordersRuntimeDao == null) {
			ordersRuntimeDao = getRuntimeExceptionDao(Order.class);
		}
		return ordersRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		itemsDao = null;
		ordersDao = null;
	}

}
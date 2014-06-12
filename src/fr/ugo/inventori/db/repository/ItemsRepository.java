package fr.ugo.inventori.db.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import fr.ugo.inventori.db.DatabaseHelper;
import fr.ugo.inventori.db.DatabaseManager;
import fr.ugo.inventori.db.model.Item;

public class ItemsRepository {

	private DatabaseHelper db;
	private Dao<Item, Integer> itemsDao;

	public ItemsRepository(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			itemsDao = db.getItemsDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}

	}

	public int create(Item item) {
		try {
			return itemsDao.create(item);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Item item) {
		try {
			return itemsDao.update(item);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Item item) {
		try {
			return itemsDao.delete(item);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	private QueryBuilder<Item, Integer> getAllQueryBuilder() {
		QueryBuilder<Item, Integer> queryBuilder = itemsDao.queryBuilder();
		return queryBuilder.orderBy("label", true);
	}
	
	public List<Item> getAll() {
		try {
			return itemsDao.query(getAllQueryBuilder().prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Item> labelLike(String pattern) {
		try {
			QueryBuilder<Item, Integer> queryBuilder = getAllQueryBuilder();
			queryBuilder.where().like("label", "%" + pattern + "%");
			return itemsDao.query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
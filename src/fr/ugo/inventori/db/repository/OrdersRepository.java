package fr.ugo.inventori.db.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import fr.ugo.inventori.db.DatabaseHelper;
import fr.ugo.inventori.db.DatabaseManager;
import fr.ugo.inventori.db.model.Order;
import fr.ugo.inventori.db.model.OrderStatusEnum;

public class OrdersRepository {

	private DatabaseHelper db;
	private Dao<Order, Integer> ordersDao;

	public OrdersRepository(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			ordersDao = db.getOrdersDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}

	}

	public int create(Order order) {
		try {
			return ordersDao.create(order);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Order order) {
		try {
			return ordersDao.update(order);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Order order) {
		try {
			return ordersDao.delete(order);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	private QueryBuilder<Order, Integer> getAllQueryBuilder() {
		return ordersDao.queryBuilder();
	}
	
	public List<Order> getAll() {
		try {
			return ordersDao.query(getAllQueryBuilder().prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Order> labelLike(String pattern) {
		try {
			QueryBuilder<Order, Integer> queryBuilder = getAllQueryBuilder();
			queryBuilder.where().like("label", "%" + pattern + "%");
			return ordersDao.query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Order> findByItemId(Long itemId, OrderStatusEnum orderStatus) {
		try {
			QueryBuilder<Order, Integer> queryBuilder = getAllQueryBuilder();
			queryBuilder.where().eq("item_id", itemId).and().eq("order_status", orderStatus);
			return ordersDao.query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
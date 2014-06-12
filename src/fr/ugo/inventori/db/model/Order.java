package fr.ugo.inventori.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Order {

	@DatabaseField(generatedId = true)
	private Long id;

	@DatabaseField(foreign = true, columnName = "item_id")
	private Item item;

	@DatabaseField(defaultValue = "0")
	private Long quantity;

	@DatabaseField(columnName = "order_status")
	private OrderStatusEnum orderStatus;

	public Order() {
	}

	public Order(Item item) {
		this(item, Long.valueOf(0));
	}

	public Order(Item item, Long quantity) {
		this.item = item;
		this.quantity = quantity;
		this.orderStatus = OrderStatusEnum.TO_REQUEST;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

}

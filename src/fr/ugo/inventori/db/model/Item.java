package fr.ugo.inventori.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Item {

	@DatabaseField(generatedId = true)
	private Long id;

	@DatabaseField
	private String label;

	@DatabaseField(defaultValue = "0")
	private Long quantity;

	private boolean checked;
	
	public Item() {
	}

	public Item(String label) {
		this(label, Long.valueOf(0));
	}
	
	public Item(String label, Long quantity) {
		this.label = label;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return label;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}

package se.bjorne.domain.shopping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingList {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
	private List<Item> items = new ArrayList<>();

	public ShoppingList() {
		super();
	}

	public ShoppingList(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	//En toString på delarna (items) triggar inte uppläsning. Så vi får göra det lite mer komplicerat...
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append("ShoppingList{" +
				"id=" + id +
				", name='" + name);

		for (Item item : getItems()) {
			buffer.append("[(" + item.getName() + "):");
			buffer.append(item);
			buffer.append("]");
		}
		buffer.append("}");
		return buffer.toString();
	}
}
